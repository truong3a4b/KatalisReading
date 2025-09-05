package com.nxt.katalisreading.data.repository

import com.nxt.katalisreading.data.mapper.Mapper.toDomain
import com.nxt.katalisreading.data.mapper.Mapper.toDto
import com.nxt.katalisreading.data.model.BookDto
import com.nxt.katalisreading.data.remote.FirebaseService
import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.GenreCustom
import com.nxt.katalisreading.domain.model.GenreRate
import com.nxt.katalisreading.domain.repository.IBookRepo

class BookRepo(
    private val firebaseService: FirebaseService
) : IBookRepo {
    override suspend fun getBanner(): List<Banner> {
        return firebaseService.getBanners().map { it.toDomain() }
    }



    override suspend fun getBooksByListGenre(typeId: String, listGenre: List<GenreRate>, quantity: Int, indexStart: Int): List<Book>{
        val listBook : MutableList<BookDto> = mutableListOf()

        for(genreRate in listGenre){
            val numBook = (genreRate.rate * quantity).toInt()
            listBook += firebaseService.getBooksByGenreId(typeId, genreRate.genreId, numBook, (indexStart * genreRate.rate).toInt())
        }
        return listBook.map { it.toDomain(firebaseService.getGenreMap()) }
    }

    override suspend fun getHotBooks(typeId : String, quantity: Int, indexStart: Int) : List<Book>{
        return firebaseService.getHotBooks(quantity = quantity, indexStart = indexStart).map { it.toDomain(firebaseService.getGenreMap()) }
    }
    override suspend fun getNewBooks(typeId : String, quantity: Int, indexStart: Int) : List<Book>{
        return firebaseService.getNewBooks(quantity = quantity, indexStart = indexStart).map { it.toDomain(firebaseService.getGenreMap()) }

    }

    override suspend fun getBooksByGenreId(
        typeId: String,
        genreId: String,
        quantity: Int,
        indexStart: Int
    ): List<Book> {
        return firebaseService.getBooksByGenreId(typeId = typeId, genreId = genreId,quantity= quantity,indexStart = indexStart).map { it.toDomain(firebaseService.getGenreMap()) }
    }

}