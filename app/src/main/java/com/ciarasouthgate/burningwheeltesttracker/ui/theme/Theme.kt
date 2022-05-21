package com.ciarasouthgate.burningwheeltesttracker.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TestTrackerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = lightColors,
        typography = AppTypography,
        content = content
    )
}

val lightColors = lightColors(
    primary = Primary,
    primaryVariant = PrimaryDark,
    secondary = Secondary,
    secondaryVariant = SecondaryDark,
    onPrimary = Color.White,
    onSecondary = Color.White
)