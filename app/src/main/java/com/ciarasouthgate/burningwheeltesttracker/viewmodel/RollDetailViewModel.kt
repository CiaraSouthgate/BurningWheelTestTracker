package com.ciarasouthgate.burningwheeltesttracker.viewmodel

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.roll.RollState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors

interface RollDetailViewModel : SkillViewModel<RollState> {
    val rollTypeIndex: State<Int>
    val rollType: State<RollType>

    fun onRollTypeChanged(index: Int)
}

class RollDetailViewModelImpl @AssistedInject constructor(
    @Assisted skillId: Long,
    repository: AppRepository
) : RollDetailViewModel, BaseSkillViewModel<RollState>(skillId, repository) {
    override lateinit var state: RollState

    private var _rollTypeIndex = mutableStateOf(0)
    override val rollTypeIndex = _rollTypeIndex

    override val rollType = derivedStateOf { RollType.values()[rollTypeIndex.value] }

    override fun performSkillInitialization(skill: Skill?) {
        state = RollState(skill!!, rollType)
    }

    override fun onRollTypeChanged(index: Int) {
        rollTypeIndex.value = index
    }

    @AssistedFactory
    interface Factory {
        fun create(skillId: Long): RollDetailViewModelImpl
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            skillId: Long
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RollDetailViewModelImpl::class.java)) {
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
): RollDetailViewModelImpl {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).rollDetailViewModelFactory()

    return viewModel(factory = RollDetailViewModelImpl.provideFactory(factory, skillId))
}