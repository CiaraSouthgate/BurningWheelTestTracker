package com.ciarasouthgate.burningwheeltesttracker.ui.list

import android.database.sqlite.SQLiteConstraintException
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: AppRepository
) : ListViewModel<Character>() {
    override suspend fun getUpdatedList(searchText: String) =
        repository.searchCharacters(searchText)

    suspend fun addCharacter(characterName: String): Boolean {
        val character = BaseCharacter(characterName)
        return try {
            repository.addCharacter(character)
            true
        } catch (e: SQLiteConstraintException) {
            false
        }
    }
}
