package com.nxt.katalisreading.data.repository

import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.BookFetchData
import com.nxt.katalisreading.domain.model.BookSection
import com.nxt.katalisreading.domain.model.HomeSectionFetchData
import com.nxt.katalisreading.domain.repository.IBookRepo
import kotlinx.coroutines.delay

class BookRepo : IBookRepo {
    override suspend fun getBanner(): List<Banner> {
        delay(500)
        val result = mutableListOf<Banner>()
        result.add(
            Banner(
                "1",
                "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877644/banner1_a0wubo.jpg"
            )
        )
        result.add(
            Banner(
                "2",
                "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner2_jhbprm.jpg"
            )
        )
        result.add(
            Banner(
                "3",
                "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner3_wxvzrl.jpg"
            )
        )

        return result
    }


    override suspend fun getBookBySection(sectionId:String, quantity: Int, indexStart: Int): List<Book> {
        delay(500)
        val result = BookFetchData.listData
        return result
    }

    override suspend fun getHomeSection(quantity: Int, indexStart: Int): List<BookSection> {
        delay(2000)
        val result = HomeSectionFetchData.dataList
        return result
    }
}