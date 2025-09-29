package com.nxt.katalisreading.presentation.screen.booklist

import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.Section

data class BookListState(
    val section : Section? = null,
    val bookList : List<Book> = emptyList(),
    val allowLoad : Boolean = true,
    val isLoading : Boolean = false,
    val isReload : Boolean = false,
)