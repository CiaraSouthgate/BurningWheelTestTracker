package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

class RollDetailViewModel @AssistedInject constructor(
    @Assisted skillId: Long,
    repository: AppRepository
) : BaseSkillViewModel(skillId, repository) {
    private val _character = MediatorLiveData<Character?>().apply {
        addSource(skill) { skill ->
            if (skill != null) viewModelScope.launch {
                value = repository.getCharacter(skill.characterId)
            }
        }
    }
    override val character: LiveData<Character?> = _character

    @AssistedFactory
    interface Factory {
        fun create(skillId: Long): RollDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            skillId: Long
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RollDetailViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(skillId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

@Composable
fun rollDetailViewModel(
    skillId: Long
): RollDetailViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).rollDetailViewModelFactory()

    return viewModel(factory = RollDetailViewModel.provideFactory(factory, skillId))
}