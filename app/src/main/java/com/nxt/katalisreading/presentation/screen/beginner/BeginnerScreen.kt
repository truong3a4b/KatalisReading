package com.nxt.katalisreading.presentation.screen.beginner

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Preview
@Composable
fun BeginnerScreenPreview() {
    MyAppTheme {
        BeginnerScreen(navController = NavController(LocalContext.current))
    }

}
@Composable
fun BeginnerScreen(
    navController: NavController
) {

}