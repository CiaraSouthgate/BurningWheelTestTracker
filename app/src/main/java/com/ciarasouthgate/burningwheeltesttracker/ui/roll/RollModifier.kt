package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.runtime.MutableState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.decrement
import com.ciarasouthgate.burningwheeltesttracker.ui.common.increment

abstract class RollModifier {
    abstract val name: String
}

data class IntRollModifier(
    override val name: String,
    val number: MutableState<Int>,
    val onIncrement: () -> Unit = { if (number.value < maxValue) number.increment() },
    val onDecrement: () -> Unit = { if (number.value > minValue) number.decrement() },
    val maxValue: Int = Int.MAX_VALUE,
    val minValue: Int = 0
) : RollModifier()

data class BooleanRollModifier(
    override val name: String,
    val value: MutableState<Boolean>,
    val onToggle: () -> Unit = {  }
) : RollModifier()