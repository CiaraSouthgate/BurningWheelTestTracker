package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.runtime.*
import com.ciarasouthgate.burningwheeltesttracker.common.ArthaType
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character

class SkillEditorState(
    val character: Character,
    initialName: String = "",
    initialShade: Shade = Shade.BLACK,
    initialExponent: Int = 1,
    initialRoutineTests: Int = 0,
    initialDifficultTests: Int = 0,
    initialChallengingTests: Int = 0,
    initialFateSpent: Int = 0,
    initialPersonaSpent: Int = 0,
    initialDeedsSpent: Int = 0
) {
    var name by mutableStateOf(initialName)
    var shade by mutableStateOf(initialShade)
    var exponent by mutableStateOf(initialExponent)

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
}

@Composable
fun rememberSkillEditorState(
    character: Character,
    name: String = "",
    shade: Shade = Shade.BLACK,
    exponent: Int = 1,
    routineTests: Int = 0,
    difficultTests: Int = 0,
    challengingTests: Int = 0,
    fateSpent: Int = 0,
    personaSpent: Int = 0,
    deedsSpent: Int = 0
) = remember(
    character,
    name,
    shade,
    exponent,
    routineTests,
    difficultTests,
    challengingTests,
    fateSpent,
    personaSpent,
    deedsSpent
) {
    SkillEditorState(
        character,
        name,
        shade,
        exponent,
        routineTests,
        difficultTests,
        challengingTests,
        fateSpent,
        personaSpent,
        deedsSpent
    )
}