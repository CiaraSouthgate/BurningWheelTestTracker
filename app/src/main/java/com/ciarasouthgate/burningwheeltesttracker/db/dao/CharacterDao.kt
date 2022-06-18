package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character

@Dao
interface CharacterDao {
    @Insert
    fun insert(character: BaseCharacter)

    @Delete
    fun delete(character: BaseCharacter)

    @Update
    fun update(character: BaseCharacter)

    @Transaction
    @Query("SELECT * FROM Character")
    fun getAll(): List<Character>
}