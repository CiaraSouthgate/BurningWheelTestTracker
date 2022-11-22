package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material2AppTheme
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
    val skills by viewModel.items.collectAsState(emptyList())
    ListScreen(
        title = viewModel.characterName.value.orEmpty(),
        onAddClicked = onAddClicked,
        viewModel = viewModel,
        emptyText = stringResource(R.string.no_skills),
        navigationIcon = navigationIcon
    ) { paddingValues ->
        ColumnListWithDividers(
            modifier = Modifier.padding(paddingValues),
            items = skills,
            key = { it.name }
        ) {
            SkillListItem(
                it,
                onSkillClicked,
                onSkillEdit,
                { viewModel.deleteListItem(it) }
            )
        }
    }
}

@Preview
@Composable
fun SkillListScreenPreview() {
    Material2AppTheme {
        SkillListScreen(
            characterId = 1,
            onAddClicked = {},
            onSkillClicked = {},
            onSkillEdit = {},
            viewModel = getSkillListViewModel()
        )
    }
}