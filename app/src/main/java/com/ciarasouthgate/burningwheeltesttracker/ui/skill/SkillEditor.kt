package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SkillEditor(
    characterId: Long,
    modifier: Modifier = Modifier,
    skillId: Long? = null,
    viewModel: SkillViewModel = skillEditorViewModel(characterId, skillId)
) {
    val state: SkillEditorState by d
}