package com.ciarasouthgate.burningwheeltesttracker.viewmodel.list

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

interface SkillListViewModel : ListViewModel<Skill> {
    val characterName: State<String?>
}

class SkillListViewModelImpl @AssistedInject constructor(
    @Assisted private val characterId: Long,
    private val repository: AppRepository
) : SkillListViewModel, ListViewModelImpl<Skill>() {
    private var _items = mutableStateOf(getAll())
    override val items: Flow<List<Skill>> by _items

    private var _character = mutableStateOf<Character?>(null)
    override val characterName: State<String?> = derivedStateOf { _character.value?.name }

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
        fun create(characterId: Long): SkillListViewModelImpl
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            characterId: Long
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SkillListViewModelImpl::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(characterId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun deleteListItem(item: Skill) {
        viewModelScope.launch {
            repository.deleteSkill(item)
        }
    }
}

@Composable
fun skillListViewModel(
    characterId: Long
) : SkillListViewModelImpl {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).skillListViewModelFactory()

    return viewModel(factory = SkillListViewModelImpl.provideFactory(factory, characterId))
}