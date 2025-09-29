package com.nxt.katalisreading.presentation.screen.bookdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.katalisreading.domain.repository.IBookRepo
import com.nxt.katalisreading.presentation.screen.booklist.BookListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.toList

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
            initChapterPage()
        }.onFailure {
            _state.value = state.value.copy(isLoading = false, isReload = true)
        }
    }


    fun updateSelectedTab(index: Int) {
        _state.value = state.value.copy(selectedTab = index)
    }

    fun initChapterPage(){
        val pageSize = 20
        val totalChapter = state.value.book?.numChapter ?: 0
        val totalPage = (totalChapter + pageSize - 1) / pageSize // làm tròn
        val currentPage = 1
        val currentChapters = (1..20).toList()
        val pages = getPageList(currentPage, totalPage, 3)
        Log.d("BookDetailVM", "pages: ${pages.size}")
        _state.value = state.value.copy(pageSize = pageSize, totalChapter = totalChapter, totalPage = totalPage, currentPage = currentPage, pages = pages, currentChapters = currentChapters)
    }

    fun updateChapterPage(pageIndex: Int){
        val currentPage = pageIndex
        val start = (currentPage - 1) * state.value.pageSize + 1
        val end = minOf(currentPage * state.value.pageSize, state.value.totalChapter)
        val currentChapters = (start..end).toList()
        val pages = getPageList(currentPage, state.value.totalPage, 3)
        _state.value = state.value.copy( currentPage = currentPage, currentChapters = currentChapters, pages = pages)
    }

    fun onSearchChange(chapter: String) {
        _state.value = state.value.copy(chapterSearch = chapter)
    }
    fun searchChapter() {
        var currentChapters: List<Int>
        //Neu co nhap chuong thi tim chuong do, khong thi lay theo trang
        if (state.value.chapterSearch.isNotEmpty() && state.value.chapterSearch.toInt() <= state.value.totalChapter) {
            currentChapters = listOf(state.value.chapterSearch.toInt())
        }else{
            val currentPage = state.value.currentPage
            val start = (currentPage - 1) * state.value.pageSize + 1
            val end = minOf(currentPage * state.value.pageSize, state.value.totalChapter)
            currentChapters = (start..end).toList()
        }
        _state.value = state.value.copy(currentChapters = currentChapters)
    }

    fun getPageList(current: Int, total: Int, maxVisible: Int = 5): List<Int?> {
        val pages = mutableListOf<Int?>()

        if (total <= maxVisible + 2) {
            // Nếu ít trang thì hiện hết
            (1..total).forEach { pages.add(it) }
        } else {
            if(current == 1) {
                (1..maxVisible).forEach { pages.add(it) }
                pages.add(null)
                pages.add(total)
            }else if(current == total){
                pages.add(1)
                pages.add(null)
                ((total - (maxVisible - 1)) until total).forEach { pages.add(it) }
                pages.add(total)
            }

            // Ở giữa
            else {
                val start = current- maxVisible / 2
                val end = current + maxVisible / 2
                if(start == 1){
                    for(i in start .. end){
                        pages.add(i)
                    }
                    pages.add(null)
                    pages.add(total)
                }
                else if(end == total){
                    pages.add(1)
                    pages.add(null)
                    for(i in start .. end){
                        pages.add(i)
                    }
                }else{
                    pages.add(1)
                    if(start != 2) pages.add(null)
                    for (i in start .. end) {
                        pages.add(i)
                    }
                    if(end != total-1) pages.add(null)
                    pages.add(total)
                }

            }

        }

        return pages
    }

    fun reload(bookId: String){
        if(state.value.isLoading) return
        _state.update { it.copy(isReload = false) }
        viewModelScope.launch {
            loadBookDetail(bookId)
        }
    }
}