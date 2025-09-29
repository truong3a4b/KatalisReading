package com.nxt.katalisreading.presentation.screen.bookdetail

import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.Review

data class BookDetailState(
    val isLoading: Boolean = false,
    val isReload: Boolean = false,
    val book: Book ?= null,
    val tabs: List<String> = listOf("Giới thiệu", "Chương", "Đánh giá"),
    val selectedTab : Int = 0,
    val chapterSearch: String = "",
    val totalChapter: Int = 0,
    val pageSize: Int = 20,
    val totalPage: Int = 0,
    val currentPage: Int = 1,
    val currentChapters: List<Int> = emptyList(),
    val pages: List<Int?> = emptyList(),
)