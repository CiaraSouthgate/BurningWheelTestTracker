package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material3AppTheme

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
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
                    containerColor = Color.Transparent
                )
            )
            SkillEditorCounter(
                label = stringResource(R.string.exponent),
                value = state.exponent,
                onIncrement = { state.exponent++ },
                onDecrement = { if (state.exponent > 1) state.exponent-- },
                alignment = Alignment.Start,
                labelPosition = LabelPosition.TOP,
                labelColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
            )
        }

        SelectorButtonGroup(
            values = Shade.values(),
            getLabelRes = { it.nameRes },
            isActive = { state.shade == it },
            onChange = { state.shade = it },
            titleRes = R.string.shade
        )

        SelectorButtonGroup(
            values = Type.values(),
            getLabelRes = { it.nameRes },
            isActive = { state.type == it },
            onChange = { state.type = it },
            titleRes = R.string.type
        )

        MultiCounterRow(
            R.string.tests_completed,
            TestType.values(),
            { it.nameRes },
            state.tests
        )

        MultiCounterRow(
            R.string.artha_spent,
            ArthaType.values(),
            { it.nameRes },
            state.arthaSpent
        )
    }
}

@Composable
private fun <T> SelectorButtonGroup(
    values: Array<T>,
    getLabelRes: (T) -> Int,
    isActive: (T) -> Boolean,
    onChange: (T) -> Unit,
    titleRes: Int? = null
) {
    SkillEditorSection(titleRes = titleRes) {
        OutlinedButtonGroup(
            values.map {
                ButtonData(
                    isActive(it),
                    { onChange(it) }
                ) {
                    Text(stringResource(getLabelRes(it)))
                }
            }
        )
    }
}

@Composable
private fun <T> MultiCounterRow(
    titleRes: Int,
    fields: Array<T>,
    getLabel: (T) -> Int,
    values: SnapshotStateMap<T, Int>,
    modifier: Modifier = Modifier
) {
    SkillEditorSection(
        titleRes = titleRes,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            fields.forEach {
                SkillEditorCounter(
                    label = stringResource(getLabel(it)),
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
private fun SkillEditorCounter(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
    labelColor: Color = Color.Unspecified,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium)
) {
    CounterWithLabel(
        label = {
            ComponentLabel(
                label,
                color = if (labelColor == Color.Unspecified) iconColor else labelColor,
                style = MaterialTheme.typography.bodySmall
            )
        },
        value = value,
        onIncrement = onIncrement,
        onDecrement = onDecrement,
        iconColor = iconColor,
        labelPosition = labelPosition,
        alignment = alignment,
        textStyle = LocalTextStyle.current,
        modifier = modifier
    )
}

@Composable
private fun SkillEditorSection(
    modifier: Modifier = Modifier,
    titleRes: Int? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        titleRes?.let {
            Text(
                stringResource(titleRes),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        Box(modifier = Modifier.padding(horizontal = 5.dp)) {
            content()
        }
    }
}

@Preview(backgroundColor = 0xffffff)
@Composable
fun SkillEditorContentPreview() {
    Material3AppTheme {
        val character = Character("Test Character", emptyList())
        val state = rememberSkillEditorState(character)
        SkillEditorContent(state, false)
    }
}