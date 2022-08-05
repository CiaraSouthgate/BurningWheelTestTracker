package com.ciarasouthgate.burningwheeltesttracker.data

import com.ciarasouthgate.burningwheeltesttracker.db.AppDatabase
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import javax.inject.Inject

interface AppRepository {
    suspend fun addCharacter(character: BaseCharacter)
    suspend fun updateCharacter(character: BaseCharacter)
    suspend fun deleteCharacter(character: BaseCharacter)
    suspend fun searchCharacters(query: String): List<Character>

    suspend fun addSkill(skill: Skill)
    suspend fun updateSkill(skill: Skill)
    suspend fun deleteSkill(skill: Skill)
    suspend fun getSkill(characterName: String, skillName: String): Skill
    suspend fun getSkillsForCharacter(characterName: String): List<Skill>
    suspend fun searchSkills(characterName: String, query: String): List<Skill>
}

class AppRepositoryImpl @Inject constructor(database: AppDatabase) : AppRepository {
    private val characterDao = database.characterDao()
    private val skillDao = database.skillDao()

    override suspend fun addCharacter(character: BaseCharacter) = characterDao.insert(character)
    override suspend fun updateCharacter(character: BaseCharacter) = characterDao.update(character)
    override suspend fun deleteCharacter(character: BaseCharacter) = characterDao.delete(character)
    override suspend fun searchCharacters(query: String) = characterDao.search(query)

    override suspend fun addSkill(skill: Skill) = skillDao.insert(skill)
    override suspend fun updateSkill(skill: Skill) = skillDao.update(skill)
    override suspend fun deleteSkill(skill: Skill) = skillDao.delete(skill)

    override suspend fun getSkillsForCharacter(characterName: String) =
        skillDao.getSkillsForCharacter(characterName)

    override suspend fun getSkill(characterName: String, skillName: String) =
        skillDao.getSkill(characterName, skillName)

    override suspend fun searchSkills(characterName: String, query: String) =
        skillDao.search(characterName, query)
}