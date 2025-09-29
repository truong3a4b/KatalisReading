package com.nxt.katalisreading.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.nxt.katalisreading.presentation.screen.auth.SignInScreen
import com.nxt.katalisreading.presentation.screen.home.HomeScreen
import com.nxt.katalisreading.presentation.screen.onboarding.OnBoardingScreen
import com.nxt.katalisreading.presentation.screen.auth.SignUpScreen
import com.nxt.katalisreading.presentation.screen.beginner.BeginnerScreen
import com.nxt.katalisreading.presentation.screen.bookdetail.BookDetailScreen
import com.nxt.katalisreading.presentation.screen.booklist.BookListScreen
import com.nxt.katalisreading.presentation.screen.folder.FolderScreen
import com.nxt.katalisreading.presentation.screen.home.HomeViewModel
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
        navigation(startDestination = "home", route = "home_graph"){
            //Home
            composable("home"){ backStackEntry ->
                // Lấy NavBackStackEntry của một graph cha gán cho HomeViewModel
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("home_graph")
                }
                val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)
                HomeScreen(navController, homeViewModel =homeViewModel, modifier = modifier)
            }

            //Book List
            composable(
                "booklist/{sectionIndex}",
                arguments = listOf(navArgument("sectionIndex"){type = NavType.StringType})
            ){backStackEntry ->
                val sectionIndex = backStackEntry.arguments?.getString("sectionIndex") ?: "0"
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("home_graph")
                }
                val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)

                BookListScreen(navController,homeViewModel = homeViewModel, sectionIndex =  sectionIndex.toInt())
            }

            //Book Detail
            composable(
                "book_detail/{bookId}",
                arguments = listOf(navArgument("bookId"){type = NavType.StringType})
            ){backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                BookDetailScreen(navController, bookId = bookId, modifier = modifier)
            }
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