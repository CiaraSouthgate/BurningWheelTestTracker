package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.common.SwipeToDelete
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.getSkillListViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.SkillListViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.skillListViewModel

@Composable
fun SkillListScreen(
    characterId: Long,
    onAddClicked: () -> Unit,
    onSkillClicked: (Skill) -> Unit,
    onSkillEdit: (Skill) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: SkillListViewModel = skillListViewModel(characterId)
) {
    val allSkills by viewModel.items.collectAsState(emptyList())
    var editDeleteDialogState by remember { mutableStateOf<EditDeleteDialogState?>(null) }
    var activeSkill by remember { mutableStateOf<Skill?>(null) }

    val groupedList by derivedStateOf { allSkills.groupBy { it.type } }
    val labelMap = mapOf(
        Type.SKILL to stringResource(R.string.skills),
        Type.STAT to stringResource(R.string.stats),
        Type.ATTRIBUTE to stringResource(R.string.attributes)
    )

    ListScreen(
        title = viewModel.characterName.value.orEmpty(),
        onAddClicked = onAddClicked,
        viewModel = viewModel,
        emptyText = stringResource(R.string.no_skills),
        navigationIcon = navigationIcon
    ) { paddingValues ->
        ListSectionWithHeader(
            modifier = Modifier.padding(paddingValues),
            grouped = groupedList,
            key = { it.id },
            labelMap = labelMap,
            spaceAtBottom = true
        ) {
            SwipeToDelete(
                onEdit = {
                    activeSkill = it
                    editDeleteDialogState = EditDeleteDialogState.EDIT
                },
                onDelete = {
                    activeSkill = it
                    editDeleteDialogState = EditDeleteDialogState.DELETE
                }
            ) {
                SkillListItem(it, onSkillClicked)
            }
        }
    }

    editDeleteDialogState?.let { state ->
        activeSkill?.let { skill ->
            EditDeleteDialog(
                entityName = skill.name,
                onDismiss = {
                    editDeleteDialogState = null
                    activeSkill = null
                },
                onConfirmAction = {
                    when (state) {
                        EditDeleteDialogState.DELETE -> viewModel.deleteListItem(skill)
                        EditDeleteDialogState.EDIT -> onSkillEdit(skill)
                    }
                },
                state = state
            )
        } ?: throw IllegalArgumentException("Active skill must be specified for edit/delete dialog")
    }
}

@Preview
@Composable
fun SkillListScreenPreview() {
    AppTheme {
        SkillListScreen(
            characterId = 1,
            onAddClicked = {},
            onSkillClicked = {},
            onSkillEdit = {},
            viewModel = getSkillListViewModel()
        )
    }
}