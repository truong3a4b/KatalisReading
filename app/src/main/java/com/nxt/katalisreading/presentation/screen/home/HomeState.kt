package com.nxt.katalisreading.presentation.screen.home

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.BookSection
import com.nxt.katalisreading.domain.model.User

data class HomeState(
    val user: User? = null,
    val banners: List<Banner> = listOf(Banner(), Banner(), Banner()),
    val sections: List<BookSection> = listOf(),
    val isLoading : Boolean = false

)
