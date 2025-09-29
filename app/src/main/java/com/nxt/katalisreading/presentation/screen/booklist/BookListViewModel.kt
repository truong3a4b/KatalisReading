package com.nxt.katalisreading.presentation.screen.booklist

import androidx.lifecycle.ViewModel
import com.nxt.katalisreading.domain.model.Section
import com.nxt.katalisreading.domain.repository.IBookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookRepo: IBookRepo
) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state: StateFlow<BookListState> = _state

    fun updateSection(section: Section) {

        _state.update { it.copy(section = section.clone(), bookList = section.listBook) }
    }

    suspend fun loadBook() {
        if(state.value.isLoading || state.value.isReload ) return
        if (state.value.section == null) {
            _state.update { it.copy(isLoading = false, isReload = true, allowLoad = false) }
        } else {
            _state.update { it.copy(isLoading = true, isReload = false, allowLoad = false) }
            val num = state.value.section!!.listBook.size
            state.value.section!!.loadBooks(bookRepo, 10, state.value.bookList.size)
            if(num == state.value.section!!.listBook.size)
                _state.update { it.copy(isLoading = false, allowLoad = false, isReload = true) }
            else
                _state.update { it.copy(bookList = state.value.section!!.listBook, isLoading = false, allowLoad = true,isReload = false) }
        }
    }

}
