package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.lifecycle.*
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RollDetailViewModel {
    val skill: LiveData<Skill?>
    val character: LiveData<Character?>
    fun saveSkill(skill: Skill)

    class Factory(
        private val skillId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RollDetailViewModelImpl::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RollDetailViewModelImpl(skillId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class RollDetailViewModelImpl(skillId: Long) : ViewModel(), RollDetailViewModel {
    @Inject
    lateinit var repository: AppRepository

    private val _skill = MutableLiveData<Skill?>(null)
    override val skill: LiveData<Skill?> = _skill

    private val _character = MediatorLiveData<Character?>().apply {
        addSource(skill) { skill ->
            if (skill != null) viewModelScope.launch {
                value = repository.getCharacter(skill.characterId)
            }
        }
    }
    override val character: LiveData<Character?> = _character

    init {
        viewModelScope.launch {
            _skill.value = repository.getSkill(skillId)
        }
    }

    override fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            repository.updateSkill(skill)
        }
    }
}