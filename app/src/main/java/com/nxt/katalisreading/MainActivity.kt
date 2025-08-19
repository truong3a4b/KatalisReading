package com.nxt.katalisreading

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.nxt.katalisreading.presentation.navigation.AppNavGraph
import com.nxt.katalisreading.presentation.navigation.Screen
import com.nxt.katalisreading.presentation.screen.auth.AuthViewModel
import com.nxt.katalisreading.presentation.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = hiltViewModel()

            val startDestination = if (authViewModel.isUserLoggedIn()) {
                Screen.Home.route
            } else {
                Screen.Onboarding.route
            }

            MyAppTheme {
                AppNavGraph(
                    navController = navController,
                    startDestination =  startDestination
                )
            }

        }
    }
}

