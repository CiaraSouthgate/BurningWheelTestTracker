package com.ciarasouthgate.burningwheeltesttracker.ui.list

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: AppRepository
) : ListViewModel<Character>() {
    private var _items by mutableStateOf(getAll())
    override val items: Flow<List<Character>> = _items

    override fun getAll(): Flow<List<Character>> = repository.getAllCharacters()

    override fun filterList(searchText: String) {
        _items = repository.searchCharacters(searchText)
    }

    suspend fun addCharacter(characterName: String): Long? {
        val character = BaseCharacter(name = characterName)
        return try {
            repository.addCharacter(character)
        } catch (e: SQLiteConstraintException) {
            null
        }
    }

    suspend fun editCharacter(character: Character): Boolean = try {
        repository.updateCharacter(character.character)
        true
    } catch (e: SQLiteConstraintException) {
        false
    }

    fun deleteCharacter(character: Character) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCharacter(character.character)
    }
}
