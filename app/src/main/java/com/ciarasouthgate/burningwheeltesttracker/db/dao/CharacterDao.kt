package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert(character: BaseCharacter)

    @Delete
    suspend fun delete(character: BaseCharacter)

    @Update
    suspend fun update(character: BaseCharacter)

    @Transaction
    @Query("SELECT * FROM Character WHERE name LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<Character>
}