package com.nxt.katalisreading.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String,
    val favoriteBooks: List<String>,
    val favoriteGenres: List<String>,
    val isBeginner: Boolean,
)