package com.nxt.katalisreading.domain.model



data class GenreCustom(
    val id: String,
    val title: String,
    val listGenre: List<GenreRate> = emptyList()
)
