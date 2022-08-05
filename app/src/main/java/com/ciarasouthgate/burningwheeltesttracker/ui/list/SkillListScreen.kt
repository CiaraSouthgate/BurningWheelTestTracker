package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun SkillListScreen(
    characterName: String,
    onAddClicked: () -> Unit,
    onSkillClicked: (Skill) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: ListViewModel<Skill> = skillListViewModel(characterName)
) {
    ListScreen(
        title = stringResource(R.string.skills_header, characterName),
        onAddClicked = onAddClicked,
        viewModel = viewModel,
        emptyText = stringResource(R.string.no_skills),
        navigationIcon = navigationIcon
    ) { paddingValues ->
        SkillList(
            skills = viewModel.items,
            onSkillClicked = onSkillClicked,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun SkillListScreenPreview() {
    val characterName = "Example Character"
    TestTrackerTheme {
        SkillListScreen(
            characterName,
            onAddClicked = {},
            onSkillClicked = {}
        )
    }
}