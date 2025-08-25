package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.BookSection

interface IBookRepo {
    suspend fun getBanner() : List<Banner>
    suspend fun getBookBySection(sectionId: String, quantity : Int, indexStart: Int) : List<Book>
    suspend fun getHomeSection(quantity: Int, indexStart: Int): List<BookSection>
}