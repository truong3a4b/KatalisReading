package com.nxt.katalisreading.data.model


data class GenreCustomDto (
    val id: String = "",
    val title: String = "",
    val listGenre: List<Pair<String, Float>> = emptyList()
)

