package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    skillNameError: Int?,
    onNameChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            TextFieldWithError(
                errorRes = skillNameError,
                modifier = Modifier.weight(3f)
            ) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = {
                        state.name = it
                        onNameChanged()
                    },
                    label = {
                        Text(
                            stringResource(
                                when (state.type) {
                                    Type.SKILL -> R.string.skill_name
                                    Type.STAT -> R.string.stat_name
                                    Type.ATTRIBUTE -> R.string.attribute_name
                                }
                            )
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    isError = skillNameError != null,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    )
                )
            }
            FormCounter(
                label = stringResource(R.string.exponent),
                value = state.exponent,
                onIncrement = { state.exponent++ },
                onDecrement = { if (state.exponent > 1) state.exponent-- },
                alignment = Alignment.Start,
                labelPosition = LabelPosition.TOP,
                labelColor = MaterialTheme.colorScheme.onSurface,
                fillHeight = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
                    .height(TextFieldDefaults.MinHeight)
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
        ) {
            if (state.type != Type.SKILL) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.success_required))
                    Switch(
                        checked = state.successRequired,
                        onCheckedChange = { state.successRequired = it }
                    )
                }
            }
        }

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
    titleRes: Int? = null,
    additionalContent: @Composable ColumnScope.() -> Unit = {}
) {
    FormSection(titleRes = titleRes) {
        Column {
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
            additionalContent()
        }
    }
}

@Preview(backgroundColor = 0xffffff)
@Composable
fun SkillEditorContentPreview() {
    AppTheme {
        val character = Character("Test Character", emptyList())
        val state = rememberSkillEditorState(character)
        SkillEditorContent(state, null, {})
    }
}