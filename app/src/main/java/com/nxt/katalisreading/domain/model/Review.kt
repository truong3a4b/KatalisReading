package com.nxt.katalisreading.domain.model

data class Review(
    val id : String,
    val userId: String,
    val avatar: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val time: Long,
)
