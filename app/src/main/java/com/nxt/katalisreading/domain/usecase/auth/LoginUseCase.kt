package com.nxt.katalisreading.domain.usecase.auth

import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IAuthRepository

class LoginUseCase(private val authRepository: IAuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.signIn(email, password)
    }
}