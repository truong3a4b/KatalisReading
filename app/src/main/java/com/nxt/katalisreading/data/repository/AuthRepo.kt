package com.nxt.katalisreading.data.repository

import com.nxt.katalisreading.data.remote.FirebaseService
import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.data.model.toDomain

class AuthRepo (
    private val firebaseService: FirebaseService
) : IAuthRepository {


    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User> {
       return firebaseService.signIn(email, password)
           .mapCatching { userDto ->
                userDto.toDomain()
           }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        return firebaseService.signUp(email, password)
    }

    override suspend fun signOut() {
        firebaseService.signOut()
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
       return firebaseService.loginWithGoogle(idToken)
           .mapCatching { userDto ->
               userDto.toDomain()
           }
    }


    override fun getCurrentUserId(): String? {
        return firebaseService.getCurrentUserId()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseService.isUserLoggedIn()
    }


}