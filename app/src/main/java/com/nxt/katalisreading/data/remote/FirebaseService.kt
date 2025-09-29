package com.nxt.katalisreading.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.nxt.katalisreading.data.model.BannerDto
import com.nxt.katalisreading.data.model.BookDto
import com.nxt.katalisreading.data.model.ChapterDto
import com.nxt.katalisreading.data.model.GenreDto
import com.nxt.katalisreading.data.model.ReviewDto
import com.nxt.katalisreading.data.model.TypeDto
import com.nxt.katalisreading.data.model.UserDto
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Review
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
//    private val realtimeDb: DatabaseReference,
    private val firestore: FirebaseFirestore
) {
    val genreCatche = mutableMapOf<String, GenreDto>()

    suspend fun signIn(
        email: String,
        password: String
    ): Result<UserDto> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user!!

            val snapshot = firestore.collection("users").document(firebaseUser.uid).get().await()
            val userDto = snapshot.toObject(UserDto::class.java)

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
            val firebaseUser = authResult.user ?: throw Exception("Không tạo được tài khoản")

            val user = UserDto(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = "",
                avatarUrl = "",
                beginner = true
            )

            // Lưu vào Realtime DB
           //realtimeDb.child("users").child(user.id).setValue(user).await()

            firestore.collection("users").document(user.id).set(user).await()

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
            //val snapshot = realtimeDb.child("users").child(firebaseUser.uid).get().await()

            val snapshot = firestore.collection("users").document(firebaseUser.uid).get().await()

            val user = if (!snapshot.exists()) {
                // Chưa có user trong DB → tạo mới
                val newUser = UserDto(
                    id = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: "",
                    avatarUrl = firebaseUser.photoUrl?.toString() ?: "",
                    beginner = true
                )
                //realtimeDb.child("users").child(firebaseUser.uid).setValue(newUser).await()
                firestore.collection("users").document(newUser.id).set(newUser).await()
                newUser
            } else {
                // Đã có user → parse dữ liệu từ DB
                snapshot.toObject(UserDto::class.java)!!
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
            //val snapshot = realtimeDb.child("users").child(id).get().await()
            val snapshot = firestore.collection("users").document(id).get().await()
            val user = snapshot.toObject(UserDto::class.java)
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
            //realtimeDb.child("users").child(userDto.id).setValue(userDto).await()
            firestore.collection("users").document(userDto.id).set(userDto, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTypes(): List<TypeDto> {
        try {
            //val snapshot = realtimeDb.child("type").get().await()
            val snapshot = firestore.collection("types").get().await()
            return snapshot.mapNotNull { it.toObject(TypeDto::class.java) }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun getGenres(): List<GenreDto> {
        try {
            //val snapshot = realtimeDb.child("genre").get().await()
            val snapshot = firestore.collection("genres").get().await()
            val listGenre = snapshot.mapNotNull { it.toObject(GenreDto::class.java) }
            for (genre in listGenre) {
                genreCatche[genre.id] = genre
            }
            return listGenre
        } catch (e: Exception) {
            return emptyList()
        }

    }
    fun getGenreMap() : Map<String, GenreDto>{
        return genreCatche
    }

    //Dựa vào list id thể loại để trả về list tên thể loại
    suspend fun convertGenre(listGenreId: List<String>) : List<String>{
        if(genreCatche.isEmpty()){
            getGenres()
        }
        val listGenre = listGenreId.mapNotNull { listGenreId ->
            genreCatche[listGenreId]?.name
        }
        return listGenre
    }

    suspend fun getBanners(): List<BannerDto> {
        try {
            //val snapshot = realtimeDb.child("banner").get().await()
            val snapshot = firestore.collection("banners").get().await()
            return snapshot.mapNotNull { it.toObject(BannerDto::class.java) }
        } catch (e: Exception) {
            return emptyList()
        }

    }

    private val bookByGenreIndex = mutableMapOf<Int, DocumentSnapshot?>()
    suspend fun getBooksByGenreId(
        typeId: String,
        genreId: String,
        quantity: Int,
        offset: Int
    ): List<BookDto>{
        try {
            Log.d("FirebaseService", "getBooksByGenreId: $genreId - $quantity - $offset")
            var query = firestore.collection("book").whereArrayContains("genre", genreId)
            if (bookByGenreIndex.containsKey(offset) && bookByGenreIndex[offset] != null) {
                val lastDoc = bookByGenreIndex[offset]
                query = query.limit(quantity.toLong()).startAfter(lastDoc) // bắt đầu từ doc sau lastDoc
                val snapshot = query.get().await()

                val bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }

                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }else{
                query = query.limit((offset + quantity).toLong())
                val snapshot = query.get().await()

                var bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)?.copy(id = doc.id)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }
                bookList = bookList.subList(offset, bookList.size)
                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }



    private val newBookIndex = mutableMapOf<Int, DocumentSnapshot?>()
    suspend fun getNewBooks(quantity: Int, offset: Int): List<BookDto> {
        try {
            var query = firestore.collection("book").orderBy("releaseTime", Query.Direction.DESCENDING)
            if (newBookIndex.containsKey(offset) && newBookIndex[offset] != null) {
                val lastDoc = newBookIndex[offset]
                query = query.limit(quantity.toLong()).startAfter(lastDoc!!.get("releaseTime")) // bắt đầu từ doc sau lastDoc
                val snapshot = query.get().await()

                val bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }

                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }else{
                query = query.limit((offset + quantity).toLong())
                val snapshot = query.get().await()

                var bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)?.copy(id = doc.id)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }
                bookList = bookList.subList(offset, bookList.size)
                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }



        } catch (e: Exception) {
            return emptyList()
        }
    }

    private val hotBookIndex = mutableMapOf<Int, DocumentSnapshot?>()
    suspend fun getHotBooks(quantity: Int, offset: Int): List<BookDto>  {
        try {
            var query = firestore.collection("book").orderBy("view", Query.Direction.DESCENDING)
            if (hotBookIndex.containsKey(offset) && hotBookIndex[offset] != null) {
                val lastDoc = hotBookIndex[offset]
                query = query.limit(quantity.toLong()).startAfter(lastDoc!!.get("view")) // bắt đầu từ doc sau lastDoc
                val snapshot = query.get().await()

                val bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }

                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }else{
                query = query.limit((offset + quantity).toLong())
                val snapshot = query.get().await()

                var bookList = mutableListOf<BookDto>()
                snapshot.documents.forEach{ doc ->
                    val book = doc.toObject(BookDto::class.java)?.copy(id = doc.id)
                    if(book != null){
                        book.genre = convertGenre(book.genre)
                        bookList.add(book)
                    }
                }
                bookList = bookList.subList(offset, bookList.size)
                val newLastDoc = snapshot.documents.lastOrNull()
                newBookIndex[offset + bookList.size] = newLastDoc
                return bookList
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun getBookById(bookId: String): BookDto? {
        return try {
            val snapshot = firestore.collection("book").document(bookId).get().await()
            val book = snapshot.toObject(BookDto::class.java)
            book?.genre = convertGenre(book.genre)
            book
        } catch (e: Exception) {
            null
        }
    }

    private val reviewIndex = mutableMapOf<Int, DocumentSnapshot?>()
    suspend fun getReviewsOfBook(bookId: String, quantity: Int, offset: Int): List<ReviewDto> {
        try {
            var query = firestore.collection("book").document(bookId).collection("reviews").orderBy("time", Query.Direction.DESCENDING)

            if (reviewIndex.containsKey(offset) && reviewIndex[offset] != null) {
                val lastDoc = reviewIndex[offset]
                query = query.limit(quantity.toLong()).startAfter(lastDoc!!.get("time")) // bắt đầu từ doc sau lastDoc
            }else{
                query = query.limit((offset + quantity).toLong())
            }
            val snapshot = query.get().await()
            val reviewList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ReviewDto::class.java)?.copy(id = doc.id)
            }
            val newLastDoc = snapshot.documents.lastOrNull()
            reviewIndex[offset + quantity] = newLastDoc
            return reviewList.takeLast(quantity)
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private val chapterIndex = mutableMapOf<Int, DocumentSnapshot?>()
    suspend fun getChaptersOfBook(bookId: String, quantity: Int, offset: Int): List<ChapterDto> {
        try{
            var query = firestore.collection("book  ").document(bookId).collection("chapters").orderBy("timeUpdate", Query.Direction.DESCENDING)
            if (chapterIndex.containsKey(offset) && chapterIndex[offset] != null) {
                val lastDoc = chapterIndex[offset]
                query = query.limit(offset.toLong()).startAfter(lastDoc!!.get("timeUpdate")) // bắt đầu từ doc sau lastDoc
            }
            val snapshot = query.get().await()
            val chapterList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ChapterDto::class.java)?.copy(id = doc.id)
            }
            val newLastDoc = snapshot.documents.lastOrNull()
            chapterIndex[offset + quantity] = newLastDoc
            return chapterList.takeLast(quantity)
        }catch (e: Exception){
            return emptyList()
        }
    }

    suspend fun getChapterContentById( chapterId: String): String? {
        return try {
            val snapshot = firestore.collection("chapters").document(chapterId).get().await()
            val chapter = snapshot.toObject(String::class.java)
            chapter
        } catch (e: Exception) {
            null
        }
    }


}