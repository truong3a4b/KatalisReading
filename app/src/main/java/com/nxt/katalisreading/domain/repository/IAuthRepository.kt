package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.domain.model.User

interface IAuthRepository{
    suspend fun signIn(
        email: String,
        password: String
    ): Result<User>

    suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun logout()
    suspend fun loginWithGoogle(idToken: String): Result<User>
    fun isUserLoggedIn(): Boolean
    fun getCurrentUserId(): String?
    fun getCurrentUser(): User?
}