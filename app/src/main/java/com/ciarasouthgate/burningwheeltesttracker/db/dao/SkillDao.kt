package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

@Dao
interface SkillDao {
    @Insert
    suspend fun insert(skill: Skill)

    @Delete
    suspend fun delete(skill: Skill)

    @Update
    suspend fun update(skill: Skill)

    @Transaction
    @Query("SELECT * FROM Skill WHERE characterName = :characterName")
    suspend fun getSkillsForCharacter(characterName: String): List<Skill>

    @Query("SELECT * FROM Skill WHERE characterName = :characterName AND name = :skillName")
    suspend fun getSkill(characterName: String, skillName: String): Skill

    @Transaction
    @Query("SELECT * FROM Skill WHERE characterName = :characterName AND name LIKE '%' || :query || '%'")
    suspend fun search(characterName: String, query: String): List<Skill>
}