package com.nxt.katalisreading.presentation.navigation


sealed class Screen(val route:String){
    object Home : Screen("home_graph")
    object Login : Screen("login")
    object Onboarding : Screen("onboarding")
    object SignUp : Screen("signup")
    object Beginner : Screen("beginner")
    object Ranking : Screen("ranking")
    object Folder : Screen("folder")
    object Profile : Screen("profile")
    object BookDetail: Screen("book_detail")
}


