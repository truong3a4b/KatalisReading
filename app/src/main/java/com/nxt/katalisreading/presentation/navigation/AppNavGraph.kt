package com.nxt.katalisreading.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nxt.katalisreading.presentation.screen.auth.SignInScreen
import com.nxt.katalisreading.presentation.screen.home.HomeScreen
import com.nxt.katalisreading.presentation.screen.onboarding.OnBoardingScreen
import com.nxt.katalisreading.presentation.screen.auth.SignUpScreen
import com.nxt.katalisreading.presentation.screen.beginner.BeginnerScreen
import com.nxt.katalisreading.presentation.screen.folder.FolderScreen
import com.nxt.katalisreading.presentation.screen.profile.ProfileScreen
import com.nxt.katalisreading.presentation.screen.ranking.RankingScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Onboarding.route,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(Screen.Home.route){
            HomeScreen(navController, modifier = modifier)
        }
        composable(Screen.Ranking.route){
            RankingScreen(navController, modifier = modifier)
        }
        composable(Screen.Folder.route){
            FolderScreen(navController, modifier = modifier)
        }
        composable(Screen.Profile.route){
            ProfileScreen(navController, modifier = modifier)
        }
        composable(Screen.Onboarding.route){
            OnBoardingScreen(navController)
        }
        composable(Screen.Login.route){
            SignInScreen(navController)
        }
        composable(Screen.SignUp.route){
            SignUpScreen(navController)
        }
        composable(Screen.Beginner.route){
            BeginnerScreen(navController)
        }
    }
}