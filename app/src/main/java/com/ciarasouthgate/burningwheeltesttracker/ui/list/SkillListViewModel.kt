package com.ciarasouthgate.burningwheeltesttracker.ui.list

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors

class SkillListViewModel @AssistedInject constructor(
    @Assisted private val characterName: String,
    private val repository: AppRepository
) : ListViewModel<Skill>() {
    override suspend fun getUpdatedList(searchText: String) =
        repository.searchSkills(characterName, searchText)

    @AssistedFactory
    interface Factory {
        fun create(characterName: String): SkillListViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            characterName: String
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SkillListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(characterName) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

@Composable
fun skillListViewModel(
    characterName: String
) : SkillListViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).skillListViewModelFactory()

    return viewModel(factory = SkillListViewModel.provideFactory(factory, characterName))
}