package com.ciarasouthgate.burningwheeltesttracker.roll

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

class RollState(exponent: Int) {
    val helpingDice = mutableStateOf(0)
    val advantageDice = mutableStateOf(0)
    val forks = mutableStateOf(0)

    val baseObstacle = mutableStateOf(0)
    val disadvantage = mutableStateOf(0)
    val otherObstacle = mutableStateOf(0)

    val obstacleDoubled = mutableStateOf(false)

    val persona = mutableStateOf(0)
    val fate = mutableStateOf(0)
    val deeds = mutableStateOf(false)

    val diceRolled by derivedStateOf {
        (exponent * if (deeds.value) 2 else 1) +
                forks.value + helpingDice.value + advantageDice.value + persona.value
    }

    val obstacle by derivedStateOf {
        (baseObstacle.value * if (obstacleDoubled.value) 2 else 1) +
                disadvantage.value + otherObstacle.value
    }
}