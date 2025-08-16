package com.nxt.katalisreading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.nxt.katalisreading.presentation.navigation.AppNavGraph
import com.nxt.katalisreading.presentation.screen.onboarding.OnBoardingScreen
import com.nxt.katalisreading.presentation.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyAppTheme {
                AppNavGraph(navController = navController)
            }

        }
    }
}

