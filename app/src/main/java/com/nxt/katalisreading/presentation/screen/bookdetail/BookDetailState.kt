package com.nxt.katalisreading.presentation.screen.bookdetail

import com.nxt.katalisreading.domain.model.Book

data class BookDetailState(
    val isLoading: Boolean = false,
    val isReload: Boolean = false,
    val book: Book ?= null,
    val tabs: List<String> = listOf("Giới thiệu", "Chương", "Đánh giá"),
    val selectedTab : Int = 0,
    val chapterSearch: String = "",
    val totalChapter: Int = 0
)