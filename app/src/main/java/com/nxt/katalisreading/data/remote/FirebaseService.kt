package com.nxt.katalisreading.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.nxt.katalisreading.data.model.BannerDto
import com.nxt.katalisreading.data.model.BookDto
import com.nxt.katalisreading.data.model.GenreDto
import com.nxt.katalisreading.data.model.TypeDto
import com.nxt.katalisreading.data.model.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val realtimeDb: DatabaseReference
) {
    val genreCatche = mutableMapOf<String, GenreDto>()

    suspend fun signIn(
        email: String,
        password: String
    ): Result<UserDto> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user!!

            val snapshot = realtimeDb.child("users").child(firebaseUser.uid).get().await()
            val userDto = snapshot.getValue(UserDto::class.java)

            if (userDto != null) {
                Result.success(userDto)
            } else {
                Result.failure(Exception("Tài khoản không tồn tại "))
            }
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthInvalidUserException -> {
                    Result.failure(IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng"))
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    Result.failure(IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng"))
                }

                else ->
                    Result.failure(e)
            }
        }
    }

    suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user!!

            val user = UserDto(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = "",
                avatarUrl = "",
                beginner = true
            )

            // Lưu vào Realtime DB
            realtimeDb.child("users").child(user.id).setValue(user).await()

            firebaseAuth.signOut()
            Result.success(Unit)
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

    suspend fun signOut() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {

        }
    }


    suspend fun loginWithGoogle(idToken: String): Result<UserDto> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: throw Exception("FirebaseUser is null")


            //Kiểm tra user trong Realtime Database
            val snapshot = realtimeDb.child("users").child(firebaseUser.uid).get().await()

            val user = if (!snapshot.exists()) {
                // Chưa có user trong DB → tạo mới
                val newUser = UserDto(
                    id = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: "",
                    avatarUrl = firebaseUser.photoUrl?.toString() ?: "",
                    beginner = true
                )
                realtimeDb.child("users").child(firebaseUser.uid).setValue(newUser).await()
                newUser
            } else {
                // Đã có user → parse dữ liệu từ DB
                snapshot.getValue(UserDto::class.java)!!
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun getUserById(id: String): Result<UserDto> {

        return try {
            val snapshot = realtimeDb.child("users").child(id).get().await()
            val user = snapshot.getValue(UserDto::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Not Found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(userDto: UserDto): Result<Unit> {
        return try {
            realtimeDb.child("users").child(userDto.id).setValue(userDto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTypes(): List<TypeDto> {
        try {
            val snapshot = realtimeDb.child("type").get().await()
            return snapshot.children.mapNotNull { it.getValue(TypeDto::class.java) }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun getGenres(): List<GenreDto> {
        try {
            val snapshot = realtimeDb.child("genre").get().await()
            val listGenre = snapshot.children.mapNotNull { it.getValue(GenreDto::class.java) }
            for (genre in listGenre) {
                genreCatche[genre.id] = genre
            }
            Log.d("TAG", "getGenreMap: $genreCatche")
            return listGenre
        } catch (e: Exception) {
            return emptyList()
        }

    }
    fun getGenreMap() : Map<String, GenreDto>{
        return genreCatche
    }
    suspend fun getBanners(): List<BannerDto> {
        try {
            val snapshot = realtimeDb.child("banner").get().await()
            return snapshot.children.mapNotNull { it.getValue(BannerDto::class.java) }
        } catch (e: Exception) {
            return emptyList()
        }

    }

    suspend fun getBooksByGenreId(
        typeId: String,
        genreId: String,
        quantity: Int,
        indexStart: Int
    ): List<BookDto> {
        try {
            val listBook: MutableList<BookDto> = mutableListOf()
            val genre = if (genreCatche.containsKey(genreId)) {
                genreCatche[genreId]
            } else {

                val snapshot = realtimeDb.child("genre").child(genreId).get().await()
                snapshot.getValue(GenreDto::class.java)

            }
            if (genre != null) {

                val listBookId = genre.listBook
                val subList = listBookId.subList(
                    indexStart,
                    (indexStart + quantity).coerceAtMost(listBookId.size)
                )
                for (bookId in subList) {
                    val bookSnapshot = realtimeDb.child("books").child(bookId).get().await()
                    val book = bookSnapshot.getValue(BookDto::class.java)
                    if (book != null) {
                        listBook.add(book)
                    }
                }

            }

            return listBook
        } catch (e: Exception) {
            return emptyList()
        }
    }


    suspend fun getNewBooks(quantity: Int, indexStart: Int): List<BookDto> {
        try {
            val listBook: MutableList<BookDto> = mutableListOf()
            val snapshot =
                realtimeDb.child("books").orderByChild("releaseTime").startAt(indexStart.toDouble())
                    .limitToLast(quantity).get().await()
            for (child in snapshot.children) {
                child.getValue(BookDto::class.java)?.let { listBook.add(it) }
            }

            return listBook
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun getHotBooks(quantity: Int, indexStart: Int): List<BookDto> {
        try {
            val listBook: MutableList<BookDto> = mutableListOf()
            val snapshot =
                realtimeDb.child("books").orderByChild("view").startAt(indexStart.toDouble())
                    .limitToLast(quantity).get().await()
            for (child in snapshot.children) {
                child.getValue(BookDto::class.java)?.let { listBook.add(it) }
            }

            return listBook
        } catch (e: Exception) {
            return emptyList()
        }
    }
}