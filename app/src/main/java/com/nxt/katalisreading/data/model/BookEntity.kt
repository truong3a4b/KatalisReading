package com.nxt.katalisreading.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nxt.katalisreading.domain.model.Chapter
import com.nxt.katalisreading.domain.model.Review

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey val id: String,
    val sectionId: String,
    val name: String,
    val image: String,
    val author: String,
    val description: String,
    val type: String,
    val genre: String,
    var rating: Float,
    var view: Int,
    val releaseTime: Long,
    var latestTimeUpdate: Long,
)
