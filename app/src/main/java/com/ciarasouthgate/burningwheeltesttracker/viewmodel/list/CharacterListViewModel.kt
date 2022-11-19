package com.ciarasouthgate.burningwheeltesttracker.viewmodel.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.addItem
import com.ciarasouthgate.burningwheeltesttracker.db.editItem
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CharacterListViewModel : ListViewModel<Character> {
    suspend fun addCharacter(characterName: String): Long?
    suspend fun editCharacter(character: Character): Boolean
}

@HiltViewModel
class CharacterListViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : CharacterListViewModel, ListViewModelImpl<Character>() {
    private var _items by mutableStateOf(getAll())
    override val items: Flow<List<Character>> = _items

    override fun getAll(): Flow<List<Character>> = repository.getAllCharacters()

    override fun filterList(searchText: String) {
        _items = repository.searchCharacters(searchText)
    }

    override suspend fun addCharacter(characterName: String): Long? {
        val character = BaseCharacter(name = characterName)
        return addItem { repository.addCharacter(character) }
    }

    override suspend fun editCharacter(character: Character): Boolean = editItem {
        repository.updateCharacter(character.character)
    }

    override fun deleteListItem(item: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacter(item.character)
        }
    }
}
