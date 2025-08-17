package com.nxt.katalisreading.presentation.screen.auth

data class AuthState(
    val email: String = "",
    val password: String = "",
    val confirm: String = "",
    val isCheckTerms: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)