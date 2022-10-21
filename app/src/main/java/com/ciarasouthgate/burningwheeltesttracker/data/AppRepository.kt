package com.ciarasouthgate.burningwheeltesttracker.data

import com.ciarasouthgate.burningwheeltesttracker.db.AppDatabase
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AppRepository {
    fun getAllCharacters(): Flow<List<Character>>
    fun searchCharacters(query: String): Flow<List<Character>>

    suspend fun addCharacter(character: BaseCharacter): Long
    suspend fun updateCharacter(character: BaseCharacter)
    suspend fun deleteCharacter(character: BaseCharacter)
    suspend fun getCharacter(id: Long): Character

    fun getSkillsForCharacter(characterId: Long): Flow<List<Skill>>
    fun searchSkills(characterId: Long, query: String): Flow<List<Skill>>

    suspend fun addSkill(skill: Skill): Long
    suspend fun updateSkill(skill: Skill)
    suspend fun deleteSkill(skill: Skill)
    suspend fun getSkill(id: Long): Skill
}

class AppRepositoryImpl @Inject constructor(database: AppDatabase) : AppRepository {
    private val characterDao = database.characterDao()
    private val skillDao = database.skillDao()

    override fun getAllCharacters(): Flow<List<Character>> = characterDao.getAll()
    override fun searchCharacters(query: String): Flow<List<Character>> = characterDao.search(query)

    override suspend fun addCharacter(character: BaseCharacter) = characterDao.insert(character)
    override suspend fun updateCharacter(character: BaseCharacter) = characterDao.update(character)
    override suspend fun deleteCharacter(character: BaseCharacter) = characterDao.delete(character)
    override suspend fun getCharacter(id: Long) = characterDao.getCharacter(id)

    override suspend fun addSkill(skill: Skill) = skillDao.insert(skill)
    override suspend fun updateSkill(skill: Skill) = skillDao.update(skill)
    override suspend fun deleteSkill(skill: Skill) = skillDao.delete(skill)

    override fun getSkillsForCharacter(characterId: Long) =
        skillDao.getSkillsForCharacter(characterId)

    override fun searchSkills(characterId: Long, query: String) =
        skillDao.search(characterId, query)

    override suspend fun getSkill(id: Long) =
        skillDao.getSkill(id)
}