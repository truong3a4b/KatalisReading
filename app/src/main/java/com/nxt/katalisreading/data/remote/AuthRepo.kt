package com.nxt.katalisreading.data.remote

import com.nxt.katalisreading.domain.repository.IAuthRepository
import kotlinx.coroutines.delay

class AuthRepo : IAuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> {
        delay(600)
        return if (email == "test@mail.com" && password == "123456") Result.success(Unit)
        else Result.failure(IllegalArgumentException("Email hoặc mật khẩu không đúng"))
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        delay(600)
        // demo: luôn thành công
        return Result.success(Unit)
    }
}