package com.nxt.katalisreading.data.repository

import com.nxt.katalisreading.data.mapper.Mapper.toDomain
import com.nxt.katalisreading.data.remote.FirebaseService
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo


class BookCategoryRepo(
    private val firebaseService: FirebaseService
) : IBookCategoryRepo {
    private val genreCatche: Map<String, Genre> = emptyMap()

    override suspend fun getTypes(): List<Type> {
        return firebaseService.getTypes().map {
            it.toDomain()
        }

    }

    override suspend fun getGenres(): List<Genre> {
        val genres = firebaseService.getGenres().map { it.toDomain() }
        for (genre in genres) {
            genreCatche.plus(genre.id to genre)
        }
        return genres
    }






}

