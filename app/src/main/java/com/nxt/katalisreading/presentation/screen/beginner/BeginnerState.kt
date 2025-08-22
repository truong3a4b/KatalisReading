package com.nxt.katalisreading.presentation.screen.beginner

import android.app.Dialog
import android.net.Uri
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type
import com.nxt.katalisreading.domain.model.User
import com.nxt.katalisreading.utils.FileUtils
import java.io.File

data class BeginnerState(
    val name: String = "Wibu12345",
    val image: File? = null,
    val imageUrl: String = "https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755699343/avatar_default_hdmpcg.png",
    val imageUri: Uri = Uri.EMPTY,
    val typeList: List<Type> = emptyList(),
    val genreList: List<Genre> = emptyList(),
    val typeChooseList: List<String> = emptyList(),
    val genreChooseList: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val nameError: Boolean = false,
    val nameMes: String = "",
    val indexPage : Int = 1,
    val numPage: Int = 2,
    val dialogMes:String?= "",
    val showDialog: Boolean = false,
    val isSuccess: Boolean = false,
)