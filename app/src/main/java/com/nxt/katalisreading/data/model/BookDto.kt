package com.nxt.katalisreading.data.model


data class BookDto(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val type: String = "",
    var genre: List<String> = emptyList(),
    val author: String = "",
    val description: String = "",
    val rating: String = "0",
    var ratingCount: List<Int> = listOf(0, 0, 0, 0, 0),
    val view: Int = 0,
    val releaseTime: Long = 0,
    val latestTimeUpdate: Long = 0,
    val numChapter: Int = 0
)