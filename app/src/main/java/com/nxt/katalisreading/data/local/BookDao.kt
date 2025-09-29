package com.nxt.katalisreading.data.local

import androidx.room.Query
import com.nxt.katalisreading.data.model.BookEntity

interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getAllBooks(): List<BookEntity>
}