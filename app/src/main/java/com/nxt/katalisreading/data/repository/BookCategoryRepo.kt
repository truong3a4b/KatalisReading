package com.nxt.katalisreading.data.repository

import com.google.firebase.database.DatabaseReference
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo
import kotlinx.coroutines.tasks.await


class BookCategoryRepo (
    private val realtimeDb: DatabaseReference
) : IBookCategoryRepo {
    override suspend fun getTypes(): List<Type> {
        val snapshot = realtimeDb.child("type").get().await()
        return snapshot.children.mapNotNull { it.getValue(Type::class.java) }
    }

    override suspend fun getGenres(): List<Genre> {
        val snapshot = realtimeDb.child("genre").get().await()
        return snapshot.children.mapNotNull { it.getValue(Genre::class.java) }
    }


}