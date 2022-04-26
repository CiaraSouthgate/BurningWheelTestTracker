package com.ciarasouthgate.burningwheeltesttracker.db.dao

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

@Dao
interface SkillDao {
    @Insert
    fun insert(skill: Skill)

    @Delete
    fun delete(skill: Skill)

    @Update
    fun update(skill: Skill)

    @Transaction
    @Query("SELECT * FROM Skill")
    fun getAll(): List<Skill>
}