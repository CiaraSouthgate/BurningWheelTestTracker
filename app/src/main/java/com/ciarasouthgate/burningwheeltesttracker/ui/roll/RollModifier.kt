package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.runtime.MutableState
import com.ciarasouthgate.burningwheeltesttracker.util.decrement
import com.ciarasouthgate.burningwheeltesttracker.util.increment

sealed class RollModifier {
    abstract val nameRes: Int
}

data class IntRollModifier(
    override val nameRes: Int,
    val number: MutableState<Int>,
    val maxValue: Int = Int.MAX_VALUE,
    val minValue: Int = 0,
    val onIncrement: () -> Unit = { if (number.value < maxValue) number.increment() },
    val onDecrement: () -> Unit = { if (number.value > minValue) number.decrement() }
) : RollModifier()

data class BooleanRollModifier(
    override val nameRes: Int,
    val value: MutableState<Boolean>,
    val onToggle: (Boolean) -> Unit = { value.value = it }
) : RollModifier()