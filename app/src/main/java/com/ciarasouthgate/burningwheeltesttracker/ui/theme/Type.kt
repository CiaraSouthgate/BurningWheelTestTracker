package com.ciarasouthgate.burningwheeltesttracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.R

val Alegreya = FontFamily(
    Font(R.font.alegreya_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.alegreya_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.alegreya_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.alegreya_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.alegreya_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.alegreya_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.alegreya_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.alegreya_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.alegreya_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.alegreya_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.alegreya_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.alegreya_blackitalic, FontWeight.Black, FontStyle.Italic)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)