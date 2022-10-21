package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {
    @Insert
    suspend fun insert(skill: Skill): Long

    @Delete
    suspend fun delete(skill: Skill)

    @Update
    suspend fun update(skill: Skill)

    @Transaction
    @Query("SELECT * FROM Skill WHERE characterId = :characterId")
    fun getSkillsForCharacter(characterId: Long): Flow<List<Skill>>

    @Transaction
    @Query("SELECT * FROM Skill WHERE characterId = :characterId AND name LIKE '%' || :query || '%'")
    fun search(characterId: Long, query: String): Flow<List<Skill>>

    @Query("SELECT * FROM Skill WHERE id = :id")
    suspend fun getSkill(id: Long): Skill
}