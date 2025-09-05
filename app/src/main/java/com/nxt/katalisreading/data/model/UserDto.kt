package com.nxt.katalisreading.data.model

import com.nxt.katalisreading.domain.model.User

data class UserDto (
    val id: String="",
    val email: String="",
    val name: String="",
    val avatarUrl: String="",
    val favoriteBooks: List<String> = emptyList(),
    val favoriteGenres: List<String> = emptyList(),
    val beginner: Boolean = false,
)
fun UserDto.toDomain() = User(
    id = id,
    email = email,
    name = name,
    avatarUrl = avatarUrl,
    favoriteBooks = favoriteBooks,
    favoriteGenres = favoriteGenres,
    isBeginner = beginner
)