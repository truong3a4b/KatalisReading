package com.nxt.katalisreading.domain.model



data class Book(
    val id: String,
    val name: String,
    val image: String,
    val type: String,
    val genre: List<String>,
    val rating: Float,
    val view: Int,
    val releaseTime: Long,
    val latestTimeUpdate: Long
)

