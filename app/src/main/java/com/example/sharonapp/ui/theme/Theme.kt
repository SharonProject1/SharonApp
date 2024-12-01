package com.example.sharonapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = Gray,
    primary = Banana,
    secondary = Orange,
    tertiary = Olive,
    primaryContainer = LightGray,
    secondaryContainer = Violet,
    onBackground = Ivory,
    onPrimary = Gray,
    onSecondary = Gray,
    onTertiary = Gray,
    onSecondaryContainer = Ivory
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