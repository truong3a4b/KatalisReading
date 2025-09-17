package com.nxt.katalisreading.domain.model

data class Chapter(
    val id: String,
    val name: String,
    val timeUpdate: Long,
    val view: Int,
    val content: String?
)
