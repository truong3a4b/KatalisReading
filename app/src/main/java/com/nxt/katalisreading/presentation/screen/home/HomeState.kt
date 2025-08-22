package com.nxt.katalisreading.presentation.screen.home

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.User

data class HomeState(
    val user: User? = null,
    val banners: List<Banner> = emptyList()
)
