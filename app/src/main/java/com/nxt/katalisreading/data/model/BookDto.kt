package com.nxt.katalisreading.data.model

import androidx.room.Update

data class BookDto(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val type: String = "",
    val genre: List<String> = emptyList(),
    val rating: String = "0",
    val view: Int = 0,
    val releaseTime: Long = 0,
    val latestTimeUpdate: Long = 0,
)