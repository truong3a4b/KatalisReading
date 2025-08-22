package com.nxt.katalisreading.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: IAuthRepository,
    private val userRepo: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUserId()
            if( userId != null) {
                val result = userRepo.getUserById(userId)
                result.onSuccess { user ->
                    _state.update { it.copy(user = user) }
                }.onFailure { e ->

                }
            }


        }
    }



}