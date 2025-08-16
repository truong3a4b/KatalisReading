package com.nxt.katalisreading.presentation.navigation


sealed class Screen(val route:String){
    object Home : Screen("home")
    object Login : Screen("login")
    object Onboarding : Screen("onboarding")
}


