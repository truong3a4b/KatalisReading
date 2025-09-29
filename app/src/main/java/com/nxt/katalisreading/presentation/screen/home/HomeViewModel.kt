package com.nxt.katalisreading.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.katalisreading.domain.model.GenreRate
import com.nxt.katalisreading.domain.model.GenreSection
import com.nxt.katalisreading.domain.model.HotBookSection
import com.nxt.katalisreading.domain.model.NewBookSection
import com.nxt.katalisreading.domain.model.RecommendSection
import com.nxt.katalisreading.domain.model.Section
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo
import com.nxt.katalisreading.domain.repository.IBookRepo
import com.nxt.katalisreading.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: IAuthRepository,
    private val userRepo: IUserRepository,
    private val bookRepo: IBookRepo,
    private val bookCategoryRepo: IBookCategoryRepo
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state


    init {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUserId()
            if (userId != null) {
                val result = userRepo.getUserById(userId)
                result.onSuccess { user ->
                    _state.update { it.copy(user = user) }
                }.onFailure { e ->

                }
            }
        }
    }

    suspend fun loadDataInit() {
        if(state.value.loaded) return
        if (state.value.isLoading || state.value.isReload) return
        //loadGenre

        _state.update { it.copy(isLoading = true) }
        val fetchGenre = bookCategoryRepo.getGenres()
        if (fetchGenre.isEmpty()) {
            _state.update { it.copy(isLoading = false, isReload = true) }
            Log.d("TAG", "loadDataInit: reload")
        } else {
            _state.update { it.copy(genres = fetchGenre, isLoading = false, isReload = false) }
            createSection()
        }

    }

    fun loadBanner() {
        if (state.value.isLoading || state.value.isReload) return
        if (_state.value.banners.isNotEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(bannerLoading = true) }
            val fetchBanners = bookRepo.getBanner()
            if (!fetchBanners.isEmpty()) {
                _state.update { it.copy(banners = fetchBanners, bannerLoading = false) }
            }
        }
    }

    fun createSection() {
        val sections = mutableListOf<Section>()

        //Tạo recommend Section
        if (state.value.isLoading || state.value.isReload || state.value.sectionLoading || state.value.sectionReload) return
        val num = state.value.genres.size - 1
        val randomNumbers = (0..num).shuffled().take(4)
        val listGenreRate = mutableListOf<GenreRate>()
        val rate = 1f / randomNumbers.size
        for (i in randomNumbers) {
            listGenreRate.add(GenreRate(state.value.genres[i].id, rate))
        }

        val recommendSection =
            RecommendSection(
                id = "0",
                title = "Đề xuất",
                type = 0,
                listBook = emptyList(),
                listGenre = listGenreRate
            )
        sections.add(recommendSection)

        //Tạo HotBookSection
        val hotBooksection = HotBookSection(id = "1", title = "Truyện hot", type = 1,listBook = emptyList())
        sections.add(hotBooksection)

        //Tạo NewBookSection
        val newBookSection = NewBookSection(id = "2", title = "Truyện mới",type = 2, listBook = emptyList())
        sections.add(newBookSection)

        //Tạo section chua book theo genre
        val listGenre = state.value.genres.take(3)
        var tmp = 3
        for (genre in listGenre) {
            val section =
                GenreSection(
                    id = "${tmp}",
                    title = genre.name,
                    type = 0,
                    listBook = emptyList(),
                    genreId = genre.id
                )
            sections.add(section)
            tmp++
        }

        _state.update { it.copy(sections = sections, sectionLoading = false, loaded = true) }
    }

    //Load book cho cac section
    fun loadBooksInSection() {
        if(state.value.sectionLoaded) return
        if (state.value.isLoading || state.value.isReload || state.value.sectionLoading || state.value.sectionReload) return
        viewModelScope.launch {
            _state.update { it.copy(sectionLoading = true) }
            val n = state.value.sections.size - 1
            for (index in 0..n) {
                val newSections = state.value.sections

                val section = state.value.sections[index]
                section.loadBooks(bookRepo, 10, section.listBook.size)
                newSections[index] = section.clone()
                _state.update { it.copy(sections = newSections) }
            }
            _state.update { it.copy(sectionLoading = false, sectionLoaded = true) }

        }
    }

    //Reload
    fun reload(){
        if(state.value.isLoading) return
        _state.update { it.copy( loaded = false, isReload = false, isLoading = false, sectionLoaded = false, banners = emptyList()) }
        viewModelScope.launch {
            loadDataInit()
            loadBanner()
            loadBooksInSection()
        }
    }
    fun refresh(){
        _state.update { it.copy(isRefreshing = true, loaded = false, isReload = false, isLoading = false, sectionLoaded = false, banners = emptyList()) }
        viewModelScope.launch {
            loadDataInit()
            loadBanner()
            loadBooksInSection()
            _state.update { it.copy(isRefreshing = false) }
        }
    }

}