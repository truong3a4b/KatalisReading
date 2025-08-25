package com.nxt.katalisreading.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.katalisreading.data.repository.BookRepo
import com.nxt.katalisreading.domain.repository.IAuthRepository
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
    private val bookRepo: IBookRepo
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state

    private val loadedSection = mutableListOf<Int>()
    private var currentPage = 0
    private val pageSize = 4

    init {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUserId()
            if( userId != null) {
                val result = userRepo.getUserById(userId)
                result.onSuccess { user ->
                    _state.update { it.copy(user = user) }
                }.onFailure { e ->

                }
            }
        }
        loadSection()
    }

    fun loadBanner(){
        viewModelScope.launch {
            val fetchBanners = bookRepo.getBanner()
            _state.update { it.copy(banners = fetchBanners) }
        }
    }

    fun loadSection(){
        if(state.value.isLoading) return
        _state.update { it.copy(isLoading = true) }
        currentPage++
        viewModelScope.launch {
            val nextSections = bookRepo.getHomeSection(currentPage,pageSize)
            _state.update { it.copy(sections = it.sections + nextSections) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun prefetchNext(){

    }

}