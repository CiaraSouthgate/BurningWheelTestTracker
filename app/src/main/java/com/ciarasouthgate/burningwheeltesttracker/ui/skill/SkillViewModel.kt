package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.launch

interface SkillViewModel {
    val skill: LiveData<Skill?>
    val character: LiveData<Character?>
    fun saveSkill(skill: Skill)
}

abstract class BaseSkillViewModel(
    private val skillId: Long?,
    protected val repository: AppRepository
) : SkillViewModel, ViewModel() {
    private val _skill = MutableLiveData<Skill?>(null)
    override val skill: LiveData<Skill?> = _skill

    init {
        viewModelScope.launch {
            skillId?.let { _skill.value = repository.getSkill(it) }
        }
    }

    override fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            repository.updateSkill(skill)
        }
    }
}