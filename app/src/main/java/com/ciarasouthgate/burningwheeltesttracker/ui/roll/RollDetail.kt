package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.roll.RollState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.LoadingPage
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerAppBar
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerDialog
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.RollDetailViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.rollDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RollDetail(
    skillId: Long,
    onSave: () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: RollDetailViewModel = rollDetailViewModel(skillId)
) {
    val selectedTabIndex by viewModel.rollTypeIndex

    viewModel.skill.value?.let { skill ->
        var saveAttempted by remember { mutableStateOf(false) }
        var showSuccessDialog by remember { mutableStateOf(false) }
        var ignoreTest by remember { mutableStateOf<Boolean?>(null) }
        Scaffold(
            topBar = {
                TestTrackerAppBar(
                    navigationIcon = navigationIcon,
                    title = skill.name,
                    actions = {
                        IconButton(onClick = { saveAttempted = true }) {
                            Icon(Icons.Default.Save, stringResource(R.string.save))
                        }
                    }
                )
            },
            bottomBar = {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    RollType.values().forEachIndexed { index, rollType ->
                        Tab(
                            text = { Text(stringResource(rollType.nameRes)) },
                            selected = selectedTabIndex == index,
                            onClick = { viewModel.onRollTypeChanged(index) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            if (viewModel.loading.value) {
                LoadingPage(modifier = Modifier.padding(paddingValues))
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RollDetailContent(
                        viewModel.state,
                        Modifier.padding(top = 5.dp)
                    )
                }
            }

            if (saveAttempted) {
                if (skill.successRequired && ignoreTest == null) {
                    showSuccessDialog = true
                } else {
                    LaunchedEffect(Unit) {
                        viewModel.editSkill(viewModel.state.getUpdatedSkill())
                        onSave()
                    }
                }
            }

            if (showSuccessDialog) {
                TestTrackerDialog(
                    onDismiss = { showSuccessDialog = false },
                    title = stringResource(R.string.test_success_title),
                    content = {
                        Text(stringResource(R.string.test_success_message, skill.name))
                    }
                ) {
                    OutlinedButton(
                        onClick = {
                            ignoreTest = true
                            showSuccessDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.no))
                    }
                    OutlinedButton(
                        onClick = {
                            ignoreTest = false
                            showSuccessDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.yes))
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 340)
@Composable
fun RollScreenPreview() {
    val skill = createTestSkill()
    AppTheme {
        RollDetail(
            1,
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
                }
            },
            onSave = {},
            viewModel = object : RollDetailViewModel {
                override val rollTypeIndex = remember { mutableStateOf(0) }
                override val rollType: State<RollType> = derivedStateOf {
                    RollType.values()[rollTypeIndex.value]
                }
                override val state: RollState = RollState(skill, rollType)

                override fun onRollTypeChanged(index: Int) {
                    rollTypeIndex.value = index
                }

                override val skill: State<Skill?> = remember { mutableStateOf(skill) }
                override val loading: State<Boolean> = remember { mutableStateOf(false) }

                override suspend fun addSkill(skill: Skill): Long = 1L
                override suspend fun editSkill(skill: Skill): Boolean = true
                override suspend fun deleteSkill(skill: Skill) {}
            }
        )
    }
}