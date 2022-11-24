package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.skill.rememberSkillEditorState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerAppBar
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.SkillViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.skillEditorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillEditor(
    characterId: Long,
    onSkillSaved: () -> Unit,
    modifier: Modifier = Modifier,
    skillId: Long? = null,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: SkillViewModel = skillEditorViewModel(characterId, skillId)
) {
    val character by viewModel.character.observeAsState()
    val skill by viewModel.skill.observeAsState()

    var skillNameError by remember { mutableStateOf<Int?>(null) }
    var isSaveAttempted by remember { mutableStateOf(false) }

    character?.let { ch ->
        val state = skill?.let { rememberSkillEditorState(ch, it) } ?: rememberSkillEditorState(ch)
        Scaffold(
            topBar = {
                TestTrackerAppBar(
                    title = skill?.name ?: "New Skill",
                    navigationIcon = navigationIcon,
                    actions = {
                        IconButton(onClick = { isSaveAttempted = true }) {
                            Icon(Icons.Default.Save, stringResource(R.string.save))
                        }
                    }
                )
            },
            modifier = modifier
        ) { paddingValues ->
            SkillEditorContent(
                state,
                skillNameError,
                onNameChanged = { skillNameError = null },
                modifier = Modifier.padding(paddingValues)
            )
        }

        if (isSaveAttempted) {
            if (state.name.isBlank()) {
                skillNameError = R.string.skill_name_required
            } else {
                LaunchedEffect(Unit) {
                    val updatedSkill = state.getSkill(ch)
                    val isSuccess = if (skillId == null) {
                        viewModel.addSkill(updatedSkill) != null
                    } else {
                        viewModel.editSkill(updatedSkill)
                    }
                    if (isSuccess) {
                        onSkillSaved()
                    } else {
                        skillNameError = R.string.skill_exists
                    }
                }
            }
            isSaveAttempted = false
        }
    }
}