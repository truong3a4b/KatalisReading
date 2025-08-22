package com.nxt.katalisreading.domain.repository

import com.nxt.katalisreading.domain.model.Banner

interface IBookRepo {
    suspend fun getBanner() : List<Banner>
}