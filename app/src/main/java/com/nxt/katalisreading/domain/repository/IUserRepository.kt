package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.domain.model.User
import java.io.File

interface IUserRepository {
    suspend fun getUserById(id: String): Result<User>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun uploadAvatar(image: File?): Result<String>
    fun getCurrentUser(): User?
}