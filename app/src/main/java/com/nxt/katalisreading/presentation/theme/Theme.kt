package com.nxt.katalisreading.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = PrimatyDark,
    onPrimary = TextDark,
    secondary = Secondary,
    background = BackgroundDark,
    onBackground = TextLight,
    errorContainer = DialogDark,
    surface = SurfaceDark,
)

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = TextLight,
    secondary = Secondary,
    background = BackgroundLight,
    onBackground = TextDark,
    errorContainer = DialogLight,
    surface = SurfaceLight
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}