package com.nxt.katalisreading

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.presentation.component.BottomBar
import com.nxt.katalisreading.presentation.navigation.AppNavGraph
import com.nxt.katalisreading.presentation.navigation.Screen
import com.nxt.katalisreading.presentation.screen.auth.AuthViewModel
import com.nxt.katalisreading.presentation.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen =  installSplashScreen()
        super.onCreate(savedInstanceState)

        val bottomBarRoute = listOf("home","ranking","folder","profile")

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route


            val authViewModel: AuthViewModel = hiltViewModel()
            val state by authViewModel.state.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                state.isLoggedIn == null
            }

            //Kiem tra nguoi dung da dang nhap chua de chon man hinh bat dau
            val startDestination = if (authViewModel.isUserLoggedIn()) {
                Screen.Home.route
            } else {
                Screen.Onboarding.route
            }
//            Toast.makeText(LocalContext.current, startDestination, Toast.LENGTH_SHORT).show()



            MyAppTheme {
                val view = LocalView.current
                val color = MaterialTheme.colorScheme.background

                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = color.toArgb()

                    val decorView = window.decorView
                    val insetsController = WindowCompat.getInsetsController(window, decorView)
                    insetsController.isAppearanceLightStatusBars = color.luminance() > 0.5f
                }

                Scaffold(
                    bottomBar = {
                        if (currentRoute in bottomBarRoute) {
                            BottomBar(
                                navController = navController
                            )
                        }
                    }
                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        startDestination =  startDestination,
                        modifier = Modifier.padding(paddingValues)
                    )
                }


            }

        }
    }
}

