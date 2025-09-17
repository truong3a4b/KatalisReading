package com.nxt.katalisreading.presentation.screen.bookdetail

import androidx.lifecycle.ViewModel
import com.nxt.katalisreading.domain.repository.IBookRepo
import com.nxt.katalisreading.presentation.screen.booklist.BookListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepo: IBookRepo
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    suspend fun loadBookDetail(bookId: String) {
        if(state.value.isLoading || state.value.isReload ) return
        _state.value = state.value.copy(isLoading = true, isReload = false)
        bookRepo.getBookDetailById(bookId).onSuccess { book ->
            _state.value = state.value.copy(book = book, isLoading = false, totalChapter = book.listChapter.size)
        }.onFailure {
            _state.value = state.value.copy(isLoading = false, isReload = true)
        }
    }

    fun updateSelectedTab(index: Int) {
        _state.value = state.value.copy(selectedTab = index)
    }
    fun onSearchChange(chapter: String) {
        _state.value = state.value.copy(chapterSearch = chapter)
    }
}