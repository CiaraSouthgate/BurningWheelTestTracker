package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

class SkillEditorViewModel @AssistedInject constructor(
    @Assisted characterId: Long,
    @Assisted skillId: Long?,
    repository: AppRepository
) : BaseSkillViewModel(skillId, repository) {
    private val _character = MutableLiveData<Character?>(null)
    override val character: LiveData<Character?> = _character

    init {
        viewModelScope.launch {
            _character.value = repository.getCharacter(characterId)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Long, skillId: Long?): SkillEditorViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            characterId: Long,
            skillId: Long?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SkillEditorViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(characterId, skillId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

@Composable
fun skillEditorViewModel(
    characterId: Long,
    skillId: Long?
): SkillEditorViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).skillEditorViewModelFactory()

    return viewModel(
        factory = SkillEditorViewModel.provideFactory(factory, characterId, skillId)
    )
}