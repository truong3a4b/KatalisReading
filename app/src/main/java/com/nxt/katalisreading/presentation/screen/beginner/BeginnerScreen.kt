package com.nxt.katalisreading.presentation.screen.beginner

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Genre
import com.nxt.katalisreading.domain.model.Type
import com.nxt.katalisreading.presentation.component.ButtonComponent
import com.nxt.katalisreading.presentation.component.Dialog
import com.nxt.katalisreading.presentation.component.Loading
import com.nxt.katalisreading.presentation.component.Logo
import com.nxt.katalisreading.presentation.component.typeDialog
import com.nxt.katalisreading.presentation.navigation.Screen




@Composable
fun BeginnerScreen(
    navController: NavController,
    vm : BeginnerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadType()
        vm.loadGenre()
    }
    if(state.showDialog){
        Dialog(
            type = if(state.isSuccess) typeDialog.SUCCESS else typeDialog.ERROR,
            mes = state.dialogMes?:"",
            onDismiss = {
                if(state.isSuccess) navController.navigate(Screen.Login.route)
                vm.consumeError()
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ){
        val(logo, box1,box2, button1,button2, button3) = createRefs()
        Logo(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )


        AnimatedContent(
            targetState = state.indexPage,
            label = "TypeBook",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(box1) {
                    top.linkTo(logo.bottom, margin = 36.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) { targetState ->
            when (targetState) {
                1 -> ProvideNameAndImage(
                    name = state.name,
                    nameError = state.nameError,
                    nameMes = state.nameMes,
                    imageUrl = state.imageUrl,
                    onNameChange = vm::onNameChange,
                    onImageChange = vm::onImageChange
                )
                2 -> ProvideTypeBook()
                else -> Text(text = "Unknown Page")
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
                .constrainAs(box2) {
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            //button back
            if(state.indexPage > 1){
                Button(
                    onClick = { vm.previousQuestion() },
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Back",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }



            Spacer(modifier = Modifier.width(8.dp))

            ButtonComponent(
                text = if(state.indexPage == state.numPage)  " Hoàn thành" else "Tiếp theo",
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    if (state.indexPage == 1) {
                        if (vm.checkName()) {
                            vm.nextQuestion()
                        }
                    } else if (state.indexPage == state.numPage) {
                        vm.submitProfile(navController)
                    }
                }
            )

        }
    }
    Loading(state.isLoading, modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f)))
}

@Composable
fun ProvideNameAndImage(
    name: String,
    nameError: Boolean = false,
    nameMes: String = "",
    imageUrl: String? = null,
    onNameChange: (String) -> Unit = {},
    onImageChange: (Uri) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var imageUri: Uri by remember { mutableStateOf(Uri.EMPTY) }
    //Chọn ảnh trong thư viện
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            imageUri = it
            onImageChange(imageUri)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ){
        Text(
            text = "Hoàn thiện trang cá nhân của bạn",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .size(120.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model =  imageUrl ?: R.drawable.avatar_default,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),

            )
            IconButton(
                onClick = { pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background,  RoundedCornerShape(8.dp))
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
                    .size(36.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.outline_photo_camera_24),
                    contentDescription = "Add Image",
                    tint = MaterialTheme.colorScheme.onBackground,

                )
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = "User name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text(
                        text = nameMes,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Nhập tên của bạn",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        )
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProvideTypeBook(
    vm : BeginnerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    ){

    val state by vm.state.collectAsState()

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (title1, title2, list1, title3, list2) = createRefs()

        Text(
            text = "Bạn muốn đọc sách gì?",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )

        Text(
            text = "Chọn một hoặc nhiều thể loại sách mà bạn muốn đọc",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Light
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title2) {
                    top.linkTo(title1.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )
        // Display the list of types
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list1) {
                    top.linkTo(title2.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.typeList.forEach { type ->
                FilterChip(
                    selected = type.id in state.typeChooseList,
                    onClick = { vm.onTypeChange(type.id) },
                    label = {
                        AsyncImage(
                            model = type.image,
                            contentDescription = "Book Icon",
                            modifier = Modifier.size(16.dp),
                            contentScale = ContentScale.Fit,

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = type.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.background,
                        labelColor = MaterialTheme.colorScheme.primary
                    ),
                )
            }
        }

        Text(
            text = "Chọn ít nhất một chủ đề mà bạn muốn đọc",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Light
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title3) {
                    top.linkTo(list1.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 4.dp)
        )

        // Display the list of genres
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list2) {
                    top.linkTo(title3.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.genreList.forEach { genre ->
                FilterChip(
                    selected = genre.id in state.genreChooseList,
                    onClick = { vm.onGenreChange(genre.id) },
                    label = {
                        AsyncImage(
                            model =  genre.image,
                            contentDescription = "Book Icon",
                            modifier = Modifier.size(16.dp),
                            contentScale = ContentScale.Fit,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = genre.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.background,
                        labelColor = MaterialTheme.colorScheme.primary
                    ),
                )
            }
        }
    }
}


