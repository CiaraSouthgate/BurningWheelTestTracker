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
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun SkillListScreen(
    characterId: Long,
    onAddClicked: () -> Unit,
    onSkillClicked: (Skill) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: SkillListViewModel = skillListViewModel(characterId)
) {
    val skills by viewModel.items.collectAsState(emptyList())
    ListScreen(
        title = viewModel.characterName.value?.let {
            stringResource(R.string.skills_header, it)
        }.orEmpty(),
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
            SkillListItem(it, onSkillClicked)
        }
    }
}

@Preview
@Composable
fun SkillListScreenPreview() {
    TestTrackerTheme {
        SkillListScreen(
            characterId = 1,
            onAddClicked = {},
            onSkillClicked = {}
        )
    }
}