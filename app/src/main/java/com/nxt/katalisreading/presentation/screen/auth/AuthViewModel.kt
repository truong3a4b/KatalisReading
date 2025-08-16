package com.nxt.katalisreading.presentation.screen.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.katalisreading.data.remote.AuthRepo
import com.nxt.katalisreading.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo : IAuthRepository = AuthRepo()) : ViewModel() {
    private var _state = MutableStateFlow(AuthState())
    val state : StateFlow<AuthState> = _state

    //Cac hàm để cập nhật trạng thái của các trường trong AuthState
    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }
    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }
    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirm = confirmPassword)
    }


    //Ham kiem tra email hop le
    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun signIn(onSuccess: () -> Unit) {
        val (email, pass) = state.value.let { it.email to it.password }
        if (!validateEmail(email)) {
            _state.value = _state.value.copy(error = "Email không hợp lệ")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val res = repo.signIn(email, pass)
            _state.value = _state.value.copy(isLoading = false)
            res.onSuccess { onSuccess() }
                .onFailure { _state.value = state.value.copy(error = it.message) }
        }
    }

    fun signUp(onSuccess: () -> Unit) {
        val (email, pass, confirm) = state.value.let { Triple(it.email, it.password, it.confirm) }
        if (!validateEmail(email)) { _state.value = _state.value.copy(error = "Email không hợp lệ"); return }
        if (pass.length < 6) { _state.value = _state.value.copy(error = "Mật khẩu tối thiểu 6 ký tự"); return }
        if (pass != confirm) { _state.value = _state.value.copy(error = "Mật khẩu nhập lại không khớp"); return }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val res = repo.signUp(email, pass)
            _state.value = _state.value.copy(isLoading = false)
            res.onSuccess { onSuccess() }
                .onFailure { _state.value = _state.value.copy(error = it.message) }
        }
    }

    fun consumeError() {
        _state.value = state.value.copy(error = null)
    }

    fun resetPassword(email: String) {
        // Implement password reset logic here
        // Update _state accordingly
    }
}