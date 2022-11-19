package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.addItem
import com.ciarasouthgate.burningwheeltesttracker.db.editItem
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.launch

interface SkillViewModel {
    val skill: LiveData<Skill?>
    val character: LiveData<Character?>
    suspend fun addSkill(skill: Skill): Long?
    suspend fun editSkill(skill: Skill): Boolean
    suspend fun deleteSkill(skill: Skill)
}

abstract class BaseSkillViewModel(
    private val skillId: Long?,
    private val repository: AppRepository
) : SkillViewModel, ViewModel() {
    private val _skill = MutableLiveData<Skill?>(null)
    override val skill: LiveData<Skill?> = _skill

    init {
        viewModelScope.launch {
            skillId?.let { _skill.value = repository.getSkill(it) }
        }
    }

    override suspend fun addSkill(skill: Skill) = addItem {
        repository.addSkill(skill)
    }

    override suspend fun editSkill(skill: Skill): Boolean = editItem {
        repository.updateSkill(skill)
    }

    override suspend fun deleteSkill(skill: Skill) {
        viewModelScope.launch {
            repository.deleteSkill(skill)
        }
    }
}