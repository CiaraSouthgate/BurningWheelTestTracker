package com.ciarasouthgate.burningwheeltesttracker.util

import androidx.compose.runtime.MutableState

fun MutableState<Int>.increment() = value++
fun MutableState<Int>.decrement() = value--

fun MutableState<Boolean>.toggle() {
    value = !value
}