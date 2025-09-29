package com.nxt.katalisreading.data.repository

import com.google.firebase.database.DatabaseReference
import com.nxt.katalisreading.data.mapper.Mapper.toDto
import com.nxt.katalisreading.data.model.toDomain
import com.nxt.katalisreading.data.remote.CloudinaryApi
import com.nxt.katalisreading.data.remote.FirebaseService
import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IUserRepository
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val firebaseService: FirebaseService,
    private val cloudinaryApi: CloudinaryApi
) : IUserRepository {

    private var currentUser: User? = null

    override suspend fun getUserById(id: String): Result<User> {

        return firebaseService.getUserById(id)
            .mapCatching { userDto ->
                userDto.toDomain().also {
                    currentUser = it
                }
            }

    }

    override suspend fun updateUser(user: User): Result<Unit> {
        val result = firebaseService.updateUser(user.toDto())
        return if (result.isSuccess) {
            currentUser = user
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }



    override suspend fun uploadAvatar(image: File?): Result<String> {

        return try{
            if( image == null || !image.exists()) {
                return Result.failure(IllegalArgumentException("Ảnh không hợp lệ hoặc không tồn tại"))
            }
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "file",
                image.name,
                requestFile
            )
            val uploadPreset = "katalis_preset".toRequestBody("text/plain".toMediaTypeOrNull())
            val response = cloudinaryApi.uploadImage(body, uploadPreset)
            Result.success(response.url)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }
    override fun getCurrentUser(): User? {
        return currentUser
    }
}