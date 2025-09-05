package com.nxt.katalisreading.presentation.screen.beginner

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo
import com.nxt.katalisreading.domain.repository.IUserRepository
import com.nxt.katalisreading.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeginnerViewModel @Inject constructor(
    private val bookCategoryRepo: IBookCategoryRepo,
    private val userRepo: IUserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(BeginnerState())
    val state: StateFlow<BeginnerState> = _state

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name, nameError = false) }
    }

    fun onImageChange(imageUri: Uri) {
        val image = FileUtils.uriToFile(imageUri, context)
        _state.update { it.copy(image = image, imageUrl = image.absolutePath) }
    }

    fun loadType(){
        viewModelScope.launch {
            val types = bookCategoryRepo.getTypes()
            _state.update { it.copy(typeList = types) }
        }
    }

    fun loadGenre() {
        viewModelScope.launch {
            val genres = bookCategoryRepo.getGenres()
            _state.update { it.copy(genreList = genres) }
        }
    }

    fun onTypeChange(typeId: String) {
        val typeList = state.value.typeChooseList.toMutableList()
        if(typeId in typeList) {
            typeList.remove(typeId)
        } else {
            typeList.add(typeId)
        }
        _state.update {
            it.copy(typeChooseList = typeList)
        }
    }
    fun onGenreChange(genreId: String) {
        val genreList = state.value.genreChooseList.toMutableList()
        if(genreId in genreList) {
            genreList.remove(genreId)
        } else {
            genreList.add(genreId)
        }
        _state.update {
            it.copy(genreChooseList = genreList)
        }

    }
    fun checkName(): Boolean {
        val name = state.value.name
        return if (name.isEmpty()) {
            _state.update { it.copy(nameError = true, nameMes = "Tên không được để trống") }
            false
        } else {
            _state.update { it.copy(nameError = false) }
            true
        }
    }
    fun nextQuestion() {
        if (state.value.indexPage < state.value.numPage) {
            _state.update { it.copy(indexPage = it.indexPage + 1) }
        } else {
            _state.update { it.copy(indexPage = 1) }
        }
    }
    fun previousQuestion() {
        if (state.value.indexPage > 1) {
            _state.update { it.copy(indexPage = it.indexPage - 1) }
        } else {
            _state.update { it.copy(indexPage = state.value.numPage) }
        }
    }
    fun submitProfile(navController: NavController) {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            //up anh len cloudinary
            if(state.value.image != null ) {
                val result = userRepo.uploadAvatar(state.value.image)

                result.onSuccess { url ->
                    _state.update { it.copy(imageUrl = url) }
                }.onFailure { e ->
                    _state.update {
                        it.copy(
                            dialogMes = "Image: ${e.message}" ?: "Network Error",
                            showDialog = true,
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                }
            }

            //update user
            var user = userRepo.getCurrentUser()
            user = user!!.copy(
                name = state.value.name,
                avatarUrl = state.value.imageUrl,
                isBeginner = false,
                favoriteBooks = state.value.typeChooseList,
                favoriteGenres = state.value.genreChooseList
            )

            //update user
            userRepo.updateUser(user).onSuccess {
                navController.navigate("home") {
                    popUpTo("0") { inclusive = true }
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        dialogMes = "User: ${e.message}" ?: "Network Error",
                        showDialog = true,
                        isSuccess = false,
                        isLoading = false
                    )
                }
            }
        }
    }
    fun consumeError() {
        _state.value = state.value.copy(dialogMes = null, showDialog = false)
    }
}