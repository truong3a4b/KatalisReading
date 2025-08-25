package com.nxt.katalisreading.domain.model

data class BookSection(
    val id: String = "",
    val title: String = "",
    val listBook: List<Book> = emptyList()
)
object HomeSectionFetchData{
    val dataList = mutableListOf<BookSection>(
        BookSection("1","Đề xuất", BookFetchData.listData),
        BookSection("2","Mới cập nhật",BookFetchData.listData),
        BookSection("3","Truyện hot",BookFetchData.listData),
        BookSection("4","Truyện mới",BookFetchData.listData),
        BookSection("5","Truyện ngôn tình",BookFetchData.listData),
        BookSection("6","Truyện kiếm hiệp",BookFetchData.listData),
        BookSection("7","Truyện trinh thám",BookFetchData.listData),
    )

}