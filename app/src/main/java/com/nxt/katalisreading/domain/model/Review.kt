package com.nxt.katalisreading.domain.model

data class Review(
    val id : String,
    val userId: String,
    val avatar: String,
    val userName: String,
    val star: Int,
    val comment: String,
)
