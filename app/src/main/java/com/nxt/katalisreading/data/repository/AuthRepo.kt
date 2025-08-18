package com.nxt.katalisreading.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepo (
    private val firebaseAuth: FirebaseAuth,
    private val realtimeDb: DatabaseReference
) : IAuthRepository {

    private var currentUser: User? = null

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user!!

            val snapshot = realtimeDb.child("users").child(firebaseUser.uid).get().await()
            val user = snapshot.getValue(User::class.java)

            if (user != null) {
                currentUser = user
                Result.success(user)
            } else {
                Result.failure(Exception("Tài khoản không tồn tại "))
            }
        } catch (e: Exception) {
            when(e) {
                is com.google.firebase.auth.FirebaseAuthInvalidUserException -> {
                    Result.failure(IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng"))
                }

                is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> {
                    Result.failure(IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng"))
                }

                else ->
                    Result.failure(e)
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user!!

            val user = User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = "",
                avatarUrl = "",
                isBeginner = true
            )

            // Lưu vào Realtime DB
            realtimeDb.child("users").child(user.id).setValue(user).await()

            currentUser = user
            Result.success(user)
        } catch (e: Exception) {
            // Xử lý lỗi nếu có
            when (e) {
                is com.google.firebase.auth.FirebaseAuthUserCollisionException -> {
                    Result.failure(IllegalArgumentException("Email đã được sử dụng"))
                }

                is com.google.firebase.auth.FirebaseAuthWeakPasswordException -> {
                    Result.failure(IllegalArgumentException("Mật khẩu quá yếu"))
                }

                else -> Result.failure(e)
            }
        }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: throw Exception("FirebaseUser is null")


            //Kiểm tra user trong Realtime Database
            val snapshot = realtimeDb.child("users").child(firebaseUser.uid).get().await()

            val user = if (!snapshot.exists()) {
                // Chưa có user trong DB → tạo mới
                val newUser = User(
                    id = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: "",
                    avatarUrl = firebaseUser.photoUrl?.toString() ?: "",
                    isBeginner = true
                )
                realtimeDb.child("users").child(firebaseUser.uid).setValue(newUser).await()
                newUser
            } else {
                // Đã có user → parse dữ liệu từ DB
                snapshot.getValue(User::class.java)!!
            }

            currentUser = user
            Result.success(user)
        }catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(uid: String): Result<User> {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): User? {
        return currentUser
    }
}