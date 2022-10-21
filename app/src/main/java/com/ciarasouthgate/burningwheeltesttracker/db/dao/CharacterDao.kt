package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert(character: BaseCharacter): Long

    @Delete
    suspend fun delete(character: BaseCharacter)

    @Update
    suspend fun update(character: BaseCharacter)

    @Transaction
    @Query("SELECT * FROM Character WHERE name LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM Character")
    fun getAll(): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacter(id: Long): Character
}