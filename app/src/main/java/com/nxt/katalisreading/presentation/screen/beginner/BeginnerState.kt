package com.nxt.katalisreading.presentation.screen.beginner

import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type

data class BeginnerState(
    val name: String = "",
    val image: String = "",
    val typeList: List<Type> = emptyList(),
    val genreList: List<Genre> = emptyList(),
    val typeChooseList: List<String> = emptyList(),
    val genreChooseList: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val nameError: Boolean = false,
    val nameMes: String = "",
    val indexPage : Int = 1,
    val numPage: Int = 2,
)