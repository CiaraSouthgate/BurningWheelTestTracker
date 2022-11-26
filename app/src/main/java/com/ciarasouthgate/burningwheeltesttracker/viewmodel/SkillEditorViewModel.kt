package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.skill.SkillEditorState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors

class SkillEditorViewModel @AssistedInject constructor(
    @Assisted skillId: Long?,
    repository: AppRepository
) : BaseSkillViewModel<SkillEditorState>(skillId, repository) {
    override lateinit var state: SkillEditorState

    override fun performSkillInitialization(skill: Skill?) {
        state = SkillEditorState(skill)
    }

    @AssistedFactory
    interface Factory {
        fun create(skillId: Long?): SkillEditorViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            skillId: Long?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SkillEditorViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(skillId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

@Composable
fun skillEditorViewModel(
    skillId: Long?
): SkillEditorViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).skillEditorViewModelFactory()

    return viewModel(
        factory = SkillEditorViewModel.provideFactory(factory, skillId)
    )
}