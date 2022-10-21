package com.ciarasouthgate.burningwheeltesttracker.ui.list

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciarasouthgate.burningwheeltesttracker.MainActivity
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SkillListViewModel @AssistedInject constructor(
    @Assisted private val characterId: Long,
    private val repository: AppRepository
) : ListViewModel<Skill>() {
    private var _items = mutableStateOf(getAll())
    override val items: Flow<List<Skill>> by _items

    private var _character = mutableStateOf<Character?>(null)
    val characterName: State<String?> = derivedStateOf { _character.value?.name }

    override fun getAll() = repository.getSkillsForCharacter(characterId)
    override fun filterList(searchText: String) {
        _items.value = repository.searchSkills(characterId, searchText)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _character.value = repository.getCharacter(characterId)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Long): SkillListViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            characterId: Long
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SkillListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(characterId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

@Composable
fun skillListViewModel(
    characterId: Long
) : SkillListViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).skillListViewModelFactory()

    return viewModel(factory = SkillListViewModel.provideFactory(factory, characterId))
}