package com.nxt.katalisreading.presentation.screen.home

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.GenreCustom
import com.nxt.katalisreading.domain.model.Section
import com.nxt.katalisreading.domain.model.User

data class HomeState(
    val user: User? = null,
    val genres: List<Genre> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val bannerLoading : Boolean = false,
    val sections: MutableList<Section> = mutableListOf(),
    val sectionLoadingIndex : Int = 0,
    val sectionLoading: Boolean = false,
    val sectionLoaded: Boolean = false,
    val sectionReload: Boolean = false,
    val isLoading : Boolean = false,
    val loaded: Boolean = false,
    val isReload: Boolean = false,
    val isRefreshing: Boolean = false,
)



