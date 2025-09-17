package com.nxt.katalisreading.data.model

data class BookDetailDto(
    val id : String = "",
    val name: String = "",
    val image: String = "",
    val type: String = "",
    val genre: List<String> = emptyList(),
    val author: String = "",
    val description: String = "",
    val rating: String = "0",
    val view: Int = 0,
    val releaseTime: Long = 0,
    val latestTimeUpdate: Long = 0,

)
