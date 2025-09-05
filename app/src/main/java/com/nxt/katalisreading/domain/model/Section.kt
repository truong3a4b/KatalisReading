package com.nxt.katalisreading.domain.model

import com.nxt.katalisreading.data.mapper.Mapper.toDomain
import com.nxt.katalisreading.domain.repository.IBookRepo

abstract class Section (
    val id: String,
    val title: String,
    val type: Int = 0,
    var listBook: List<Book>,
){
    abstract suspend fun loadBooks(bookRepo : IBookRepo, quantity : Int, indexStart : Int)
}

class RecommendSection(
    id: String,
    title: String,
    type: Int,
    listBook: List<Book>,
    val listGenre : List<GenreRate>
) : Section(id,title,type,listBook) {
    override suspend fun loadBooks(bookRepo : IBookRepo, quantity : Int, indexStart : Int) {
        val fetchBooks = bookRepo.getBooksByListGenre(typeId = "0", listGenre = listGenre, quantity = 12, indexStart = listBook.size)
        listBook += fetchBooks
    }
}

class HotBookSection(
    id: String,
    title: String,
    type: Int,
    listBook: List<Book>,
): Section(id,title,type,listBook) {
    override suspend fun loadBooks(
        bookRepo: IBookRepo,
        quantity: Int,
        indexStart: Int,
    ) {
        this.listBook += bookRepo.getHotBooks(typeId = "0", quantity = 15, indexStart = listBook.size)
    }
}

class NewBookSection(
    id: String,
    title: String,
    type: Int,
    listBook: List<Book>,

) : Section(id,title,type,listBook) {
    override suspend fun loadBooks(
        bookRepo: IBookRepo,
        quantity: Int,
        indexStart: Int,
    ) {
        this.listBook += bookRepo.getNewBooks(typeId = "0", quantity = 6, indexStart = listBook.size)
    }
}

class GenreSection(
    id: String,
    title: String,
    type: Int,
    listBook: List<Book>,
    val genreId: String,

) : Section(id,title,type, listBook) {
    override suspend fun loadBooks(
        bookRepo: IBookRepo,
        quantity: Int,
        indexStart: Int,
    ) {
        this.listBook += bookRepo.getBooksByGenreId(typeId = "0", genreId = genreId, quantity = 10, indexStart = listBook.size)
    }
}