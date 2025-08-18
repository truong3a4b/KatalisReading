package com.nxt.katalisreading.domain.usecase.auth

import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IAuthRepository

class LoginWithGoogleUC(
    private val authRepository: IAuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<User> {
        return authRepository.loginWithGoogle(idToken)
    }
}