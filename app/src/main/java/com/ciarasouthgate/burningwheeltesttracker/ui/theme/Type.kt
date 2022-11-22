package com.ciarasouthgate.burningwheeltesttracker.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.R
import androidx.compose.material.Typography as M2Typography
import androidx.compose.material3.Typography as M3Typography

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

val AlegreyaSans = FontFamily(
    Font(R.font.alegreyasans_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.alegreyasans_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.alegreyasans_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.alegreyasans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.alegreyasans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.alegreyasans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.alegreyasans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.alegreyasans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.alegreyasans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.alegreyasans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.alegreyasans_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.alegreyasans_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.alegreyasans_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.alegreyasans_blackitalic, FontWeight.Black, FontStyle.Italic)
)

val Bitter = FontFamily(
    Font(R.font.bitter_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.bitter_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.bitter_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.bitter_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.bitter_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.bitter_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.bitter_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.bitter_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.bitter_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.bitter_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.bitter_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.bitter_blackitalic, FontWeight.Black, FontStyle.Italic)
)

val M3Typography = M3Typography(
    displayLarge = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Light,
        fontSize = 108.sp,
        letterSpacing = (-1.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Light,
        fontSize = 68.sp,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 54.sp,
        letterSpacing = 0.sp
    ),
    // TODO headlineLarge
    headlineMedium = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        letterSpacing = 0.25.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 23.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 1.25.sp
    ),
    // TODO labelMedium
    labelSmall = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp
    )
)

val AppTypography = M2Typography(
    h1 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Light,
        fontSize = 108.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Light,
        fontSize = 68.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 54.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 23.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = AlegreyaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp
    )
)

//val AppTypography = Typography(
//    displayLarge = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 57.sp,
//        lineHeight = 64.sp,
//        letterSpacing = (-0.25).sp,
//    ),
//    displayMedium = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 45.sp,
//        lineHeight = 52.sp,
//        letterSpacing = 0.sp,
//    ),
//    displaySmall = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 36.sp,
//        lineHeight = 44.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineLarge = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineMedium = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 28.sp,
//        lineHeight = 36.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineSmall = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleLarge = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleMedium = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Medium,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    titleSmall = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    labelLarge = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.25.sp,
//    ),
//    bodySmall = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.4.sp,
//    ),
//    labelMedium = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Medium,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    labelSmall = TextStyle(
//        fontFamily = Alegreya,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
//    ),
//)