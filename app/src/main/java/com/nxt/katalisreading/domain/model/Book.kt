package com.nxt.katalisreading.domain.model

data class Book(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val type: String = "",
    val genre: List<String> = emptyList(),
    val rating: Float = 4.5f,
    val view: Int = 1000
)

object BookFetchData {
    public val listData = mutableListOf<Book>(
        Book(
            "1",
            "Maze Runner  : The scorch trials ",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877644/banner1_a0wubo.jpg",
            "Novel",
            listOf("Kiếm hiệp", "Lãng mạn")
        ),
        Book(
            "2",
            "Eighty six Vol 9 : Valkyrie Has Landed",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner2_jhbprm.jpg",
            "Light Novel",
            listOf("Tu tiên", "Ngôn tình", "LGBT")
        ),
        Book(
            "3",
            "The Fragrant flower Blooms With dignity vol 7",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner3_wxvzrl.jpg",
            "Manga",
            listOf("Romance", "Hentai", "LGBT")
        ),
        Book(
            "4",
            "Maze Runner  : The scorch trials ",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877644/banner1_a0wubo.jpg",
            "Novel",
            listOf("Kiếm hiệp", "Lãng mạn")
        ),
        Book(
            "5",
            "Eighty six Vol 9 : Valkyrie Has Landed",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner2_jhbprm.jpg",
            "Light Novel",
            listOf("Tu tiên", "Ngôn tình", "LGBT")
        ),
        Book(
            "6",
            "The Fragrant flower Blooms With dignity vol 7",
            "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755877645/banner3_wxvzrl.jpg",
            "Manga",
            listOf("Romance", "Hentai", "LGBT")
        )
    )
}