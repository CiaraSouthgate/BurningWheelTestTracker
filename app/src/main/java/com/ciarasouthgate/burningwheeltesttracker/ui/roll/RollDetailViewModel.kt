package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.lifecycle.*
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RollDetailViewModel {
    val skill: LiveData<Skill?>
    fun saveSkill(skill: Skill)

    class Factory(
        private val characterName: String,
        private val skillName: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RollDetailViewModelImpl::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RollDetailViewModelImpl(characterName, skillName) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class RollDetailViewModelImpl(
    characterName: String, skillName: String
) : ViewModel(), RollDetailViewModel {
    @Inject
    lateinit var repository: AppRepository

    private val _skill = MutableLiveData<Skill?>(null)
    override val skill: LiveData<Skill?> = _skill

    init {
        viewModelScope.launch {
            _skill.value = repository.getSkill(characterName, skillName)
        }
    }

    override fun saveSkill(skill: Skill) {
        viewModelScope.launch {
            repository.updateSkill(skill)
        }
    }
}