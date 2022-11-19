package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.roll.rememberRollState
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.SkillViewModel
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.rollDetailViewModel

@Composable
fun RollDetail(
    skillId: Long,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: SkillViewModel = rollDetailViewModel(skillId)
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val rollType by derivedStateOf { RollType.values()[selectedTabIndex] }
    var onSaveAttempted by remember { mutableStateOf(false) }

    val skill = viewModel.skill.observeAsState()
    val character = viewModel.character.observeAsState()
    if (skill.value != null) {
        val rollState = rememberRollState(skill.value!!)
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    navigationIcon = navigationIcon,
                    title = { Text(character.value?.name.orEmpty()) },
                    actions = {
                        IconButton(
                            onClick = { onSaveAttempted = true }
                        ) {
                            Icon(Icons.Default.Save, stringResource(R.string.save))
                        }
                    }
                )
            },
            bottomBar = {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    RollType.values().forEachIndexed { index, rollType ->
                        Tab(
                            text = { Text(rollType.name, fontSize = 12.sp) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RollDetailContent(
                    rollState,
                    rollType,
                    Modifier.padding(top = 10.dp)
                )
            }
        }

        if (onSaveAttempted) {
            LaunchedEffect(Unit) {
                viewModel.editSkill(rollState.updateSkill())
            }
        }
    }
}

@Preview(widthDp = 340)
@Composable
fun RollScreenPreview() {
    val characterName = "Test Character"
    val skillName = "Test Skill"
    AppTheme {
        RollDetail(
            1,
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
                }
            },
            object : SkillViewModel {
                override val skill = MutableLiveData(createTestSkill(skillName = skillName))
                override val character = MutableLiveData(Character(name = characterName))
                override suspend fun addSkill(skill: Skill): Long = 1L
                override suspend fun editSkill(skill: Skill): Boolean = true
                override suspend fun deleteSkill(skill: Skill) {}
            }
        )
    }
}