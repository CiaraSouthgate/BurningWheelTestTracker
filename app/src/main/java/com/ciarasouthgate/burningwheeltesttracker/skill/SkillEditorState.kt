package com.ciarasouthgate.burningwheeltesttracker.skill

import androidx.compose.runtime.*
import com.ciarasouthgate.burningwheeltesttracker.common.ArthaType
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class SkillEditorState(
    val character: Character,
    initialName: String = "",
    initialType: Type = Type.SKILL,
    initialShade: Shade = Shade.BLACK,
    initialExponent: Int = 1,
    initialRoutineTests: Int = 0,
    initialDifficultTests: Int = 0,
    initialChallengingTests: Int = 0,
    initialFateSpent: Int = 0,
    initialPersonaSpent: Int = 0,
    initialDeedsSpent: Int = 0,
    initialSuccessRequired: Boolean = false
) {
    var name by mutableStateOf(initialName)
    var shade by mutableStateOf(initialShade)
    var exponent by mutableStateOf(initialExponent)
    var type by mutableStateOf(initialType)
    var successRequired by mutableStateOf(initialSuccessRequired)

    val tests = mutableStateMapOf(
        TestType.ROUTINE to initialRoutineTests,
        TestType.DIFFICULT to initialDifficultTests,
        TestType.CHALLENGING to initialChallengingTests
    )

    val arthaSpent = mutableStateMapOf(
        ArthaType.FATE to initialFateSpent,
        ArthaType.PERSONA to initialPersonaSpent,
        ArthaType.DEEDS to initialDeedsSpent
    )

    fun getSkill(character: Character) = Skill(
        characterId = character.id,
        name = name,
        exponent = exponent,
        type = type,
        shade = shade,
        routineTests = tests[TestType.ROUTINE]!!,
        difficultTests = tests[TestType.DIFFICULT]!!,
        challengingTests = tests[TestType.CHALLENGING]!!,
        fateSpent = arthaSpent[ArthaType.FATE]!!,
        personaSpent = arthaSpent[ArthaType.PERSONA]!!,
        deedsSpent = arthaSpent[ArthaType.DEEDS]!!,
        successRequired = successRequired
    )
}

@Composable
fun rememberSkillEditorState(
    character: Character,
    skill: Skill
) = rememberSkillEditorState(
    character,
    skill.name,
    skill.type,
    skill.shade,
    skill.exponent,
    skill.routineTests,
    skill.difficultTests,
    skill.challengingTests,
    skill.fateSpent,
    skill.personaSpent,
    skill.deedsSpent,
    skill.successRequired
)

@Composable
fun rememberSkillEditorState(
    character: Character,
    name: String = "",
    type: Type = Type.SKILL,
    shade: Shade = Shade.BLACK,
    exponent: Int = 1,
    routineTests: Int = 0,
    difficultTests: Int = 0,
    challengingTests: Int = 0,
    fateSpent: Int = 0,
    personaSpent: Int = 0,
    deedsSpent: Int = 0,
    successRequired: Boolean = false
) = remember(
    character,
    name,
    type,
    shade,
    exponent,
    routineTests,
    difficultTests,
    challengingTests,
    fateSpent,
    personaSpent,
    deedsSpent,
    successRequired
) {
    SkillEditorState(
        character,
        name,
        type,
        shade,
        exponent,
        routineTests,
        difficultTests,
        challengingTests,
        fateSpent,
        personaSpent,
        deedsSpent,
        successRequired
    )
}