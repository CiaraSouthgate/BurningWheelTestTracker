package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.ArthaType
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.skill.SkillEditorState
import com.ciarasouthgate.burningwheeltesttracker.skill.rememberSkillEditorState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.*
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

@Composable
fun SkillEditorContent(
    state: SkillEditorState,
    skillNameError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        AddSkillSection(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(verticalAlignment = Alignment.Top) {
                    TextField(
                        value = state.name,
                        onValueChange = { state.name = it },
                        label = {
                            Text(
                                stringResource(
                                    if (state.type == Type.SKILL) R.string.skill_name else R.string.stat_name
                                )
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        isError = skillNameError,
                        modifier = Modifier.weight(3f),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
                    )
                    LabelledComponent(
                        label = { SkillEditorLabel(stringResource(R.string.is_stat)) }
                    ) {
                        Switch(
                            checked = state.type == Type.STAT,
                            onCheckedChange = {
                                state.type = if (it) Type.STAT else Type.SKILL
                            }
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShadeSelector(
                        value = state.shade,
                        onChanged = { state.shade = it },
                        modifier = Modifier.weight(2f)
                    )
                    AddSkillCounter(
                        label = stringResource(R.string.exponent),
                        value = state.exponent,
                        onIncrement = { state.exponent++ },
                        onDecrement = { if (state.exponent > 1) state.exponent-- },
                        labelPosition = LabelPosition.TOP,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }

        MultiCounterRow(
            R.string.tests_completed,
            TestType.values(),
            { it.prettyName },
            state.tests
        )

        MultiCounterRow(
            R.string.artha_spent,
            ArthaType.values(),
            { it.prettyName },
            state.arthaSpent
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShadeSelector(
    value: Shade,
    onChanged: (Shade) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = value.prettyName,
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(R.string.shade)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            isError = false
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Shade.values().forEach {
                DropdownMenuItem(onClick = {
                    onChanged(it)
                    expanded = false
                }) {
                    Text(it.prettyName)
                }
            }
        }
    }
}

@Composable
fun SkillEditorLabel(text: String) {
    val labelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
    ComponentLabel(text, color = labelColor, style = MaterialTheme.typography.caption)
}

@Composable
private fun AddSkillCounter(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
) {
    val labelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
    CounterWithLabel(
        label = { SkillEditorLabel(label) },
        value = value,
        onIncrement = onIncrement,
        onDecrement = onDecrement,
        iconColor = labelColor,
        labelPosition = labelPosition,
        textStyle = LocalTextStyle.current,
        modifier = modifier
    )
}

@Composable
private fun <T> MultiCounterRow(
    titleRes: Int,
    fields: Array<T>,
    getLabel: (T) -> String,
    values: SnapshotStateMap<T, Int>,
    modifier: Modifier = Modifier
) {
    AddSkillSection(
        titleRes = titleRes,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            fields.forEach {
                AddSkillCounter(
                    label = getLabel(it),
                    value = values[it]!!,
                    onIncrement = { values[it] = values[it]!! + 1 },
                    onDecrement = {
                        val value = values[it]!!
                        if (value > 0) values[it] = value - 1
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun AddSkillSection(
    modifier: Modifier = Modifier,
    titleRes: Int? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        titleRes?.let {
            Text(
                stringResource(titleRes),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        Box(modifier = Modifier.padding(horizontal = 5.dp)) {
            content()
        }
    }
}

@Preview
@Composable
fun SkillEditorContentPreview() {
    AppTheme {
        val character = Character("Test Character", emptyList())
        val state = rememberSkillEditorState(character)
        SkillEditorContent(state, false)
    }
}