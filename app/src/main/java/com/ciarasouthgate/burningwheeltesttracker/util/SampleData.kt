package com.ciarasouthgate.burningwheeltesttracker.util

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_TESTS_NEEDED
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.CharacterListViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.SkillListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

fun createTestCharacters(num: Int): List<Character> {
    return (1..num).map { i ->
        val name = "Character Name $i"
        val numSkills = Random.nextInt(1, 20)

        val skills = createTestSkillList(numSkills)

        Character(name, skills)
    }
}

fun createTestSkillList(num: Int): List<Skill> {
    return (1..num).map { j ->
        createRandomTestSkill(
            "Test Skill $j"
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

fun createRandomTestSkill(
    skillName: String = "Test Skill",
    type: Type = Type.SKILL
) = Skill(
    name = skillName,
    characterId = 0,
    exponent = Random.nextInt(1, MAX_EXPONENT),
    type = type,
    routineTests = Random.nextInt(0, MAX_TESTS_NEEDED),
    difficultTests = Random.nextInt(0, MAX_TESTS_NEEDED),
    challengingTests = Random.nextInt(0, MAX_TESTS_NEEDED),
    fateSpent = Random.nextInt(0, MAX_TESTS_NEEDED),
    personaSpent = Random.nextInt(0, MAX_TESTS_NEEDED),
    deedsSpent = Random.nextInt(0, MAX_TESTS_NEEDED)
)


fun createTestSkill(
    skillName: String = "Test Skill",
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
    name = skillName,
    characterId = 0,
    exponent = exponent,
    type = type,
    shade = shade,
    routineTests = routine,
    difficultTests = difficult,
    challengingTests = challenging,
    fateSpent = fateSpent,
    personaSpent = personaSpent,
    deedsSpent = deedsSpent
)

fun getCharacterListViewModel(
    characterList: List<Character> = createTestCharacters(5)
) = object : CharacterListViewModel {
    override suspend fun addCharacter(characterName: String): Long? = null
    override suspend fun editCharacter(character: Character): Boolean = false
    override fun deleteCharacter(character: Character) {}

    override var items: Flow<List<Character>> = flowOf(characterList)
    override fun getAll(): Flow<List<Character>> = flowOf(characterList)


    override fun filterList(searchText: String) {
        items = flowOf(characterList.filter { it.name.contains(searchText) })
    }

    override fun onSearchTextChanged(text: String) {
        filterList(text)
    }
}

fun getSkillListViewModel(
    skillList: List<Skill> = createTestSkillList(5)
) = object : SkillListViewModel {
    override val characterName: State<String?> = mutableStateOf("Test Character")
    override var items: Flow<List<Skill>> = flowOf(skillList)
    override fun getAll(): Flow<List<Skill>> = flowOf(skillList)

    override fun filterList(searchText: String) {
        items = flowOf(skillList.filter { it.name.contains(searchText) })
    }

    override fun onSearchTextChanged(text: String) {
        filterList(text)
    }

}