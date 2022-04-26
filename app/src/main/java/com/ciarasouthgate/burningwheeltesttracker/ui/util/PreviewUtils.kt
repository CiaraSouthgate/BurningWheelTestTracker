package com.ciarasouthgate.burningwheeltesttracker.ui.util

import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.util.MAX_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.util.MAX_TESTS_NEEDED
import kotlin.random.Random

fun createTestCharacters(num: Int): List<Character> {
    return (1..num).map { i ->
        val name = "Character Name $i"
        val numSkills = Random.nextInt(1, 20)

        val skills = createTestSkillList(numSkills, name)

        Character(name, skills)
    }
}

fun createTestSkillList(num: Int, characterName: String): List<Skill> {
    return (1..num).map { j ->
        createTestSkill(
            "Test Skill $j",
            characterName,
            Random.nextInt(1, MAX_EXPONENT),
            routine = Random.nextInt(0, MAX_TESTS_NEEDED),
            difficult = Random.nextInt(0, MAX_TESTS_NEEDED),
            challenging = Random.nextInt(0, MAX_TESTS_NEEDED),
            fateSpent = Random.nextInt(0, MAX_TESTS_NEEDED),
            personaSpent = Random.nextInt(0, MAX_TESTS_NEEDED),
            deedsSpent = Random.nextInt(0, MAX_TESTS_NEEDED)
        )
    }
}

fun createTestStatList(): List<Skill> {
    return listOf("Will", "Power", "Agility", "Perception", "Forte", "Speed").map {
        createTestSkill(
            it,
            type = Type.STAT,
            exponent = Random.nextInt(0, MAX_EXPONENT)
        )
    }
}

fun createTestSkill(
    skillName: String = "Test Skill",
    characterName: String = "Test Character",
    exponent: Int = 3,
    routine: Int = 0,
    difficult: Int = 0,
    challenging: Int = 0,
    fateSpent: Int = 0,
    personaSpent: Int = 0,
    deedsSpent: Int = 0,
    shade: Shade = Shade.BLACK,
    type: Type = Type.SKILL
) = Skill(
    skillName,
    characterName,
    type,
    exponent,
    shade,
    routine,
    difficult,
    challenging,
    fateSpent,
    personaSpent,
    deedsSpent
)