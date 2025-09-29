package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.data.model.BookDto
import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.GenreCustom
import com.nxt.katalisreading.domain.model.GenreRate

interface IBookRepo {
    suspend fun getBanner() : List<Banner>
    suspend fun getBooksByListGenre(typeId: String, listGenre: List<GenreRate>, quantity: Int, indexStart: Int): List<Book>
    suspend fun getHotBooks(typeId : String, quantity: Int, indexStart: Int) : List<Book>
    suspend fun getNewBooks(typeId : String, quantity: Int, indexStart: Int) : List<Book>
    suspend fun getBooksByGenreId(typeId : String, genreId : String, quantity: Int, indexStart: Int) : List<Book>
    suspend fun getBookDetailById(bookId: String) : Result<Book>
}