package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.addItem
import com.ciarasouthgate.burningwheeltesttracker.db.editItem
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.launch

interface SkillViewModel<T> {
    val skill: State<Skill?>
    val state: T
    val loading: State<Boolean>

    suspend fun addSkill(skill: Skill): Long?
    suspend fun editSkill(skill: Skill): Boolean
    suspend fun deleteSkill(skill: Skill)
}

abstract class BaseSkillViewModel<T> (
    private val skillId: Long?,
    private val repository: AppRepository
) : SkillViewModel<T>, ViewModel() {
    private val _skill = mutableStateOf<Skill?>(null)
    override val skill: State<Skill?> = _skill

    private val _loading = mutableStateOf(true)
    override val loading: State<Boolean> = _loading

    init {
        viewModelScope.launch {
            skillId?.let { id ->
                _skill.value = repository.getSkill(id).also {
                    performSkillInitialization(it)
                }
            } ?: performSkillInitialization(null)
            _loading.value = false
        }
    }

    abstract fun performSkillInitialization(skill: Skill?)

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