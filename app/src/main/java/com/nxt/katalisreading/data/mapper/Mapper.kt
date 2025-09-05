package com.nxt.katalisreading.data.mapper

import com.nxt.katalisreading.data.model.BannerDto
import com.nxt.katalisreading.data.model.BookDto
import com.nxt.katalisreading.data.model.GenreCustomDto
import com.nxt.katalisreading.data.model.GenreDto
import com.nxt.katalisreading.data.model.TypeDto
import com.nxt.katalisreading.data.model.UserDto
import com.nxt.katalisreading.data.repository.BookCategoryRepo
import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.GenreCustom
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type
import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo

object Mapper{

    fun User.toDto() = UserDto(
        id = id,
        email = email,
        name = name,
        avatarUrl = avatarUrl,
        favoriteBooks = favoriteBooks,
        favoriteGenres = favoriteGenres,
        beginner = isBeginner
    )

    fun TypeDto.toDomain() = Type(
        id = this.id,
        name = this.name,
        image = this.image
    )
    fun Type.toDto(): TypeDto = TypeDto(
        id = this.id,
        name = this.name,
        image = this.image
    )

    fun GenreDto.toDomain(): Genre = Genre(
        id = this.id,
        name = this.name,
        image = this.image,
        hide = this.hide
    )
    fun Genre.toDto(): GenreDto = GenreDto(
        id = this.id,
        name = this.name,
        image = this.image,
        hide = this.hide

    )



    fun BookDto.toDomain(genreMap: Map<String, GenreDto>): Book = Book(
        id = this.id,
        name = this.name,
        image = this.image,
        type = this.type,
        genre = this.genre.mapNotNull { genreMap[it]?.name },
        rating = this.rating.toFloat(),
        view = this.view,
        releaseTime = this.releaseTime,
        latestTimeUpdate = this.latestTimeUpdate
    )

    fun Book.toDto(): BookDto = BookDto(
        id = this.id,
        name = this.name,
        image = this.image,
        type = this.type,
        genre = this.genre,
        rating = this.rating.toString(),
        view = this.view,
        releaseTime = this.releaseTime,
        latestTimeUpdate = this.latestTimeUpdate
    )

    fun BannerDto.toDomain(): Banner = Banner(
        id = this.id,
        image = this.image
    )
    fun Banner.toDto(): BannerDto = BannerDto(
        id = this.id,
        image = this.image
    )




}