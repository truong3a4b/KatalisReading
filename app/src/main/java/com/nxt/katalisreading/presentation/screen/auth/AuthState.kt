package com.nxt.katalisreading.presentation.screen.auth



data class AuthState(
    val email: String = "",
    val password: String = "",
    val confirm: String = "",
    val isCheckTerms: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showMes : Boolean = false,
    val showDialog: Boolean = false,
    val emailError : Boolean = false,
    val emailMes: String = "",
    val passwordError : Boolean = false,
    val passwordMes: String = "",
    val confirmError : Boolean = false,
    val confirmMes: String = "",
    val isSuccess: Boolean = false
)