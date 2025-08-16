package com.nxt.katalisreading.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nxt.katalisreading.presentation.screen.home.HomeScreen
import com.nxt.katalisreading.presentation.screen.onboarding.OnBoardingScreen
import com.nxt.katalisreading.presentation.screen.splash.SplashScreen


@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ){
        composable(Screen.Home.route){
            HomeScreen()
        }
        composable(Screen.Onboarding.route){
            OnBoardingScreen(navController)
        }
        composable(Screen.Login.route){
            // LoginScreen(navController)

        }
    }
}