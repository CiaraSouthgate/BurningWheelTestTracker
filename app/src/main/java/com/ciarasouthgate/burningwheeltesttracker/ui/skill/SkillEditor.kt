package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.skill.SkillEditorState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.LoadingPage
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerAppBar
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.SkillViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.skillEditorViewModel
import timber.log.Timber

private const val TAG = "SkillEditor"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillEditor(
    characterId: Long,
    onSkillSaved: () -> Unit,
    modifier: Modifier = Modifier,
    skillId: Long? = null,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: SkillViewModel<SkillEditorState> = skillEditorViewModel(skillId)
) {
    var skillNameError by remember { mutableStateOf<Int?>(null) }
    var isSaveAttempted by remember { mutableStateOf(false) }

    val skill by viewModel.skill
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
        if (viewModel.loading.value) {
            LoadingPage()
        } else {
            SkillEditorContent(
                viewModel.state,
                skillNameError,
                onNameChanged = { skillNameError = null },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    if (isSaveAttempted) {
        if (viewModel.state.name.isBlank()) {
            skillNameError = R.string.skill_name_required
        } else {
            LaunchedEffect(Unit) {
                val updatedSkill = viewModel.state.getSkill(characterId)
                val isSuccess = if (skillId == null) {
                    val id = viewModel.addSkill(updatedSkill)
                    Timber.d("$TAG: Added skill with ID $id")
                    id != null
                } else {
                    viewModel.editSkill(updatedSkill).also {
                        Timber.d("$TAG: Edited skill")
                    }
                }
                if (isSuccess) {
                    Timber.d("$TAG: success; calling onSkillSaved()")
                    onSkillSaved()
                } else {
                    skillNameError = R.string.skill_exists
                }
            }
        }
        isSaveAttempted = false
    }
}