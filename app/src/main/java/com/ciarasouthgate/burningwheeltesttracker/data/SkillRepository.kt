package com.ciarasouthgate.burningwheeltesttracker.data

import com.ciarasouthgate.burningwheeltesttracker.db.AppDatabase
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class SkillRepository(database: AppDatabase) {
    private val characterDao = database.characterDao()
    private val skillDao = database.skillDao()

    val characters = characterDao.getAll()
    fun addCharacter(character: BaseCharacter) = characterDao.insert(character)
    fun updateCharacter(character: BaseCharacter) = characterDao.update(character)
    fun deleteCharacter(character: BaseCharacter) = characterDao.delete(character)

    fun getSkillsForCharacter(characterName: String) =
        skillDao.getSkillsForCharacter(characterName)
    fun getSkill(characterName: String, skillName: String) =
        skillDao.getSkill(characterName, skillName)
    fun addSkill(skill: Skill) = skillDao.insert(skill)
    fun updateSkill(skill: Skill) = skillDao.update(skill)
    fun deleteSkill(skill: Skill) = skillDao.delete(skill)
}