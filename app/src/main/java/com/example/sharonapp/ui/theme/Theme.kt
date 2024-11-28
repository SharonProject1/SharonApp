package com.example.sharonapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = LightViolet,
    secondary = Violet,
    tertiary = DarkViolet,
    background = Gray,
    onBackground = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White
)

@Composable
fun SharonAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}