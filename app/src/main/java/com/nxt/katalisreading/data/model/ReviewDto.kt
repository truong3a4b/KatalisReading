package com.nxt.katalisreading.data.model

data class ReviewDto(
    val id : String = "",
    val userId: String = "",
    val avatar: String = "",
    val userName: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val time: Long = 0,
)