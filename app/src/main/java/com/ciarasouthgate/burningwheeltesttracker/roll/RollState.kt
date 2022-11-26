package com.ciarasouthgate.burningwheeltesttracker.roll

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class RollState(
    val skill: Skill,
    val rollType: State<RollType>
) {
    val helpingDice = mutableStateOf(0)
    val advantageDice = mutableStateOf(0)
    val forks = mutableStateOf(0)
    val wounds = mutableStateOf(0)

    val baseObstacle = mutableStateOf(1)
    val disadvantage = mutableStateOf(0)
    val otherObstacle = mutableStateOf(0)

    val obstacleDoubled = mutableStateOf(false)

    val persona = mutableStateOf(0)
    val fate = mutableStateOf(0)
    val deeds = mutableStateOf(0)

    val diceRolled by derivedStateOf {
        (skill.exponent * if (deeds.value > 0) 2 else 1) +
                forks.value +
                helpingDice.value +
                advantageDice.value +
                persona.value -
                minOf(wounds.value, skill.exponent)
    }

    val obstacle by derivedStateOf {
        (baseObstacle.value * if (obstacleDoubled.value) 2 else 1) +
                disadvantage.value + otherObstacle.value
    }

    var userSpecifiedDifficulty = mutableStateOf(TestType.ROUTINE)

    val difficulty by derivedStateOf {
        when {
            rollType.value == RollType.GRADUATED -> TestType.ROUTINE
            diceRolled == 1 && obstacle == 1 -> userSpecifiedDifficulty.value
            else -> getTestType(diceRolled, obstacle)
        }
    }

    fun getUpdatedSkill(ignoreTest: Boolean = false): Skill {
        if (!ignoreTest) {
            skill.addTestAndCheckUpgrade(difficulty)
        }
        skill.spendArthaAndCheckAdvancement(
            fate.value,
            persona.value,
            deeds.value > 0
        )
        return skill
    }

    private fun getTestType(dice: Int, obstacle: Int): TestType {
        if (obstacle > dice) return TestType.CHALLENGING
        val difficultDiff = if (dice > 6) 2 else if (dice > 3) 1 else 0
        return if (dice - obstacle <= difficultDiff) TestType.DIFFICULT else TestType.ROUTINE
    }
}