package com.nxt.katalisreading.domain.model

data class User(
    val id: String="",
    val email: String="",
    val name: String="",
    val avatarUrl: String? = null,
    val favoriteBooks: List<String> = emptyList(),
    val favoriteGenres: List<String> = emptyList(),
    val beginner: Boolean = false,
)