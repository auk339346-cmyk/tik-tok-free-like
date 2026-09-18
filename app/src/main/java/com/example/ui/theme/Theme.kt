package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = TikTokNeonPink,
    onPrimary = TikTokWhite,
    secondary = TikTokCyan,
    onSecondary = TikTokDarkBg,
    tertiary = TikTokGold,
    onTertiary = TikTokDarkBg,
    background = TikTokDarkBg,
    onBackground = TikTokWhite,
    surface = TikTokCardBg,
    onSurface = TikTokWhite,
    surfaceVariant = TikTokSurface,
    onSurfaceVariant = TikTokTextSecondary,
    outline = TikTokSurfaceElevated
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}

