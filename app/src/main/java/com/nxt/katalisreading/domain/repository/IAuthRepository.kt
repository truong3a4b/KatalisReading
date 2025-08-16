package com.nxt.katalisreading.domain.repository

interface IAuthRepository{
    suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit>
}