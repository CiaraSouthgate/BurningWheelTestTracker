package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.runtime.MutableState

fun MutableState<Int>.increment() = value++
fun MutableState<Int>.decrement() = value--

fun MutableState<Boolean>.toggle() {
    value = !value
}