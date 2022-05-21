package com.ciarasouthgate.burningwheeltesttracker.roll

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class RollDetailViewModel(val skill: Skill) : ViewModel() {
    private var _selectedTabIndex by mutableStateOf(0)
    val selectedTabIndex: Int = _selectedTabIndex

    val rollType by derivedStateOf { RollType.values()[_selectedTabIndex] }

    val rollState = RollState(skill.exponent)

    fun onSelectedTabChanged(index: Int) {
        _selectedTabIndex = index
    }

    fun onSave() {
        val diceValue = rollState.diceRolled - rollState.persona.value
        val testType = getTestType(diceValue, rollState.obstacle)
        skill.addTestAndCheckUpgrade(testType)
        skill.spendArthaAndCheckAdvancement(
            rollState.fate.value,
            rollState.persona.value,
            rollState.deeds.value
        )
    }

    private fun getTestType(dice: Int, obstacle: Int): TestType {
        if (obstacle > dice) return TestType.CHALLENGING
        val difficultDiff = if (obstacle > 6) 2 else if (obstacle > 3) 1 else 0
        return if (dice - obstacle > difficultDiff) TestType.DIFFICULT else TestType.ROUTINE
    }
}

class RollDetailViewModelFactory(private val skill: Skill) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RollDetailViewModel::class.java)) {
            return RollDetailViewModel(skill) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}