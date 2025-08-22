package com.nxt.katalisreading.data.repository

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.repository.IBookRepo
import kotlinx.coroutines.delay

class BookRepo : IBookRepo{
    override suspend fun getBanner(): List<Banner> {
        delay(500)
        val result = mutableListOf<Banner>()
        result.add(Banner("1", "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877644/banner1_a0wubo.jpg"))
        result.add(Banner("2", "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner2_jhbprm.jpg"))
        result.add(Banner("3", "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner3_wxvzrl.jpg"))

        return result
    }
}