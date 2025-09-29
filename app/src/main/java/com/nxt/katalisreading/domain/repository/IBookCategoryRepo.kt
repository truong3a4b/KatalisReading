package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type

interface IBookCategoryRepo {
    suspend fun getTypes(): List<Type>
    suspend fun getGenres(): List<Genre>

}