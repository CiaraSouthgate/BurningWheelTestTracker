package com.ciarasouthgate.burningwheeltesttracker.skill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ciarasouthgate.burningwheeltesttracker.common.ArthaType
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

class SkillEditorState(private val skill: Skill?) {
    var name by mutableStateOf(skill?.name.orEmpty())
    var shade by mutableStateOf(skill?.shade ?: Shade.BLACK)
    var exponent by mutableStateOf(skill?.exponent ?: 1)
    var type by mutableStateOf(skill?.type ?: Type.SKILL)
    var successRequired by mutableStateOf(skill?.successRequired ?: false)

    val tests = mutableStateMapOf(
        TestType.ROUTINE to (skill?.routineTests ?: 0),
        TestType.DIFFICULT to (skill?.difficultTests ?: 0),
        TestType.CHALLENGING to (skill?.challengingTests ?: 0)
    )

    val arthaSpent = mutableStateMapOf(
        ArthaType.FATE to (skill?.fateSpent ?: 0),
        ArthaType.PERSONA to (skill?.personaSpent ?: 0),
        ArthaType.DEEDS to (skill?.deedsSpent ?: 0)
    )

    fun getSkill(characterId: Long) = Skill(
        characterId = characterId,
        id = skill?.id ?: 0,
        name = name.trim(),
        exponent = exponent,
        type = type,
        shade = shade,
        routineTests = tests[TestType.ROUTINE]!!,
        difficultTests = tests[TestType.DIFFICULT]!!,
        challengingTests = tests[TestType.CHALLENGING]!!,
        fateSpent = arthaSpent[ArthaType.FATE]!!,
        personaSpent = arthaSpent[ArthaType.PERSONA]!!,
        deedsSpent = arthaSpent[ArthaType.DEEDS]!!,
        successRequired = type != Type.SKILL && successRequired
    )
}