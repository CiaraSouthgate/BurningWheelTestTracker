package com.ciarasouthgate.burningwheeltesttracker.util

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale

fun String.capitalizeWords(locale: Locale): String =
    split(" ").joinToString(" ") { it.lowercase().capitalize(locale) }
