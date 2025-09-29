package com.nxt.katalisreading.domain.model

data class Book(
    val id: String,
    val name: String,
    val image: String,
    val type: String,
    val genre: List<String>,
    val author: String,
    val description: String,
    var rating: Float,
    var ratingCount: List<Int>,
    var view: Int,
    val releaseTime: Long,
    var latestTimeUpdate: Long,
    var listReview: List<Review>,
    var listChapter: List<Chapter>,
    val numChapter: Int
)

