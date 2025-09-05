package com.nxt.katalisreading.presentation.screen.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: IAuthRepository) : ViewModel() {
    private var _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    //Cac hàm để cập nhật trạng thái của các trường trong AuthState
    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, emailError = false)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, passwordError = false)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirm = confirmPassword, confirmError = false)
    }

    fun onCheckTermsChange(isChecked: Boolean) {
        _state.value = _state.value.copy(isCheckTerms = isChecked)
    }

    init {
        // Kiểm tra session ngay khi ViewModel khởi tạo
        _state.update { it.copy(isLoggedIn = repo.isUserLoggedIn()) }
    }

    // Hàm xử lý khi mất focus trên trường email
    fun checkEmail(): Boolean {
        val email = state.value.email
        if (email.isEmpty()) {
            _state.value =
                _state.value.copy(emailError = true, emailMes = "Email không được để trống")
            return false
        } else if (!validateEmail(email)) {
            _state.value = _state.value.copy(emailError = true, emailMes = "Email không hợp lệ")
            return false
        } else {
            _state.value = _state.value.copy(emailError = false)
            return true
        }
    }

    //Ham xử lý khi mất focus trên trường mật khẩu
    fun checkPassword(): Boolean {
        val password = state.value.password
        if (password.isEmpty()) {
            _state.value = _state.value.copy(
                passwordError = true,
                passwordMes = "Mật khẩu không được để trống"
            )
            return false
        } else if (password.length < 6) {
            _state.value =
                _state.value.copy(passwordError = true, passwordMes = "Mật khẩu tối thiểu 6 ký tự")
            return false
        } else {
            _state.value = _state.value.copy(passwordError = false)
            return true
        }
    }

    fun onFocusPasswordLost() {
        val password = state.value.password
        if (password.isEmpty()) {
            return
        } else if (password.length < 6) {
            _state.value =
                _state.value.copy(passwordError = true, passwordMes = "Mật khẩu tối thiểu 6 ký tự")
        }
    }

    fun onFocusConfirmLost() {
        val confirm = state.value.confirm
        if (confirm.isEmpty()) {
            return
        } else if (confirm != state.value.password) {
            _state.value =
                _state.value.copy(confirmError = true, confirmMes = "Mật khẩu xác nhận không khớp")
        }
    }

    //Ham xử lý khi mất focus trên trường xác nhận mật khẩu
    fun checkConfirmPassword(): Boolean {
        val confirmPassword = state.value.confirm
        if (confirmPassword.isEmpty()) {
            _state.value = _state.value.copy(
                confirmError = true,
                confirmMes = "Mật khẩu xác nhận không được để trống"
            )
            return false
        } else if (confirmPassword != state.value.password) {
            _state.value =
                _state.value.copy(confirmError = true, confirmMes = "Mật khẩu xác nhận không khớp")
            return false
        } else {
            _state.value = _state.value.copy(confirmError = false)
            return true
        }
    }

    //Ham kiem tra email hop le
    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //Goi khi dang nhap
    fun signIn(navController: NavController) {
        val (email, pass) = state.value.let { it.email to it.password }
        if (!checkEmail()) return
        if (pass.isEmpty()) {
            _state.value = _state.value.copy(
                passwordError = true,
                passwordMes = "Mật khẩu không được để trống"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val res = repo.signIn(email, pass)
            _state.value = _state.value.copy(isLoading = false)
            res.onSuccess { user ->
                navController.navigate(Screen.Home.route) {
                    popUpTo("0") { inclusive = true }
                }

            }
                .onFailure {
                    _state.value = state.value.copy(
                        dialogMes = it.message,
                        showDialog = true,
                        isSuccess = false
                    )
                }
        }
    }

    //Ham goi khi dang ky
    fun signUp() {
        val (email, pass, confirm) = state.value.let { Triple(it.email, it.password, it.confirm) }

        if (!checkEmail()) return
        if (!checkPassword()) return
        if (!checkConfirmPassword()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val res = repo.signUp(email, pass)
            _state.value = _state.value.copy(isLoading = false)
            res.onSuccess {
                _state.update {
                    it.copy(
                        showDialog = true,
                        dialogMes = "Đăng ký thành công, vui lòng đăng nhập",
                        isSuccess = true
                    )
                }
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        dialogMes = it.message,
                        showDialog = true,
                        isSuccess = false
                    )
                }
        }
    }

    //Dang nhap voi google
    fun signInWithGoogle(idToken: String, navController: NavController) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val res = repo.loginWithGoogle(idToken)
            _state.value = _state.value.copy(isLoading = false)
            res.onSuccess { user ->

                navController.navigate("home") {
                    popUpTo("0") { inclusive = true }
                }
            }.onFailure {
                _state.value =
                    state.value.copy(dialogMes = it.message, showDialog = true, isSuccess = false)
            }
        }
    }

    //Nhan ok trong dialog thong bao loi
    fun consumeError() {
        _state.value = state.value.copy(dialogMes = null, showDialog = false)
    }

    fun resetPassword(email: String) {

    }

    //Kiem tra nguoi dung da dang nhap hay chua
    fun isUserLoggedIn(): Boolean {
        return repo.isUserLoggedIn()
    }
}