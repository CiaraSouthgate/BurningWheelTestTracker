package com.ciarasouthgate.burningwheeltesttracker.roll

import androidx.compose.runtime.*
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class RollState(
    val skill: Skill,
    initialHelpingDice: Int = 0,
    initialAdvantageDice: Int = 0,
    initialForks: Int = 0,
    initialBaseObstacle: Int = 0,
    initialDisadvantage: Int = 0,
    initialOtherObstacle: Int = 0,
    initialObstacleDoubled: Boolean = false,
    initialPersona: Int = 0,
    initialFate: Int = 0,
    initialDeeds: Boolean = false
) {
    val helpingDice = mutableStateOf(initialHelpingDice)
    val advantageDice = mutableStateOf(initialAdvantageDice)
    val forks = mutableStateOf(initialForks)

    val baseObstacle = mutableStateOf(initialBaseObstacle)
    val disadvantage = mutableStateOf(initialDisadvantage)
    val otherObstacle = mutableStateOf(initialOtherObstacle)

    val obstacleDoubled = mutableStateOf(initialObstacleDoubled)

    val persona = mutableStateOf(initialPersona)
    val fate = mutableStateOf(initialFate)
    val deeds = mutableStateOf(initialDeeds)

    val diceRolled by derivedStateOf {
        (skill.exponent * if (deeds.value) 2 else 1) +
                forks.value + helpingDice.value + advantageDice.value + persona.value
    }

    val obstacle by derivedStateOf {
        (baseObstacle.value * if (obstacleDoubled.value) 2 else 1) +
                disadvantage.value + otherObstacle.value
    }

    val skillName = skill.name

    fun save() {
        val diceValue = diceRolled - persona.value
        val testType = getTestType(diceValue, obstacle)
        skill.addTestAndCheckUpgrade(testType)
        skill.spendArthaAndCheckAdvancement(
            fate.value,
            persona.value,
            deeds.value
        )
    }

    private fun getTestType(dice: Int, obstacle: Int): TestType {
        if (obstacle > dice) return TestType.CHALLENGING
        val difficultDiff = if (obstacle > 6) 2 else if (obstacle > 3) 1 else 0
        return if (dice - obstacle > difficultDiff) TestType.DIFFICULT else TestType.ROUTINE
    }
}

@Composable
fun rememberRollState(
    skill: Skill,
    helpingDice: Int = 0,
    advantageDice: Int = 0,
    forks: Int = 0,
    baseObstacle: Int = 0,
    disadvantage: Int = 0,
    otherObstacle: Int = 0,
    obstacleDoubled: Boolean = false,
    persona: Int = 0,
    fate: Int = 0,
    deeds: Boolean = false
) = remember(
    skill,
    helpingDice,
    advantageDice,
    forks,
    baseObstacle,
    disadvantage,
    otherObstacle,
    obstacleDoubled,
    persona,
    fate,
    deeds
) {
    RollState(
        skill,
        helpingDice,
        advantageDice,
        forks,
        baseObstacle,
        disadvantage,
        otherObstacle,
        obstacleDoubled,
        persona,
        fate,
        deeds
    )
}