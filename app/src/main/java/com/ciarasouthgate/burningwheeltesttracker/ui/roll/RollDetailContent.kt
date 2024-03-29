package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_PERSONA
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.roll.RollState
import com.ciarasouthgate.burningwheeltesttracker.ui.common.FormCounter
import com.ciarasouthgate.burningwheeltesttracker.ui.common.FormSection
import com.ciarasouthgate.burningwheeltesttracker.ui.list.ListSectionWithHeader
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Bitter
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill
import com.ciarasouthgate.burningwheeltesttracker.util.decrement
import com.ciarasouthgate.burningwheeltesttracker.util.increment

@Composable
fun RollDetailContent(
    rollState: RollState,
    modifier: Modifier = Modifier
) {
    val rollType by rollState.rollType
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            NumberDisplay(
                value = rollState.diceRolled,
                label = stringResource(R.string.dice_rolled)
            )
            if (rollType != RollType.GRADUATED) {
                Text(
                    stringResource(R.string.vs),
                    style = MaterialTheme.typography.headlineMedium
                )
                NumberDisplay(
                    value = rollState.obstacle,
                    label = stringResource(
                        when (rollType) {
                            RollType.VERSUS -> R.string.opponent
                            else -> R.string.obstacle
                        }
                    )
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val specifiable = rollState.diceRolled == 1 && rollState.obstacle == 1
            var difficulty by rollState.userSpecifiedDifficulty
            val showLeftArrow =
                specifiable && difficulty == TestType.DIFFICULT
            val showRightArrow =
                specifiable && difficulty == TestType.ROUTINE
            IconButton(
                onClick = { difficulty = TestType.ROUTINE },
                enabled = showLeftArrow,
                modifier = Modifier.alpha(if (showLeftArrow) 1.0f else 0.0f)
            ) {
                Icon(Icons.Default.ArrowLeft, stringResource(R.string.difficulty_decrease))
            }
            Text(
                stringResource(rollState.difficulty.nameRes),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFeatureSettings = "smcp"
                )
            )
            IconButton(
                onClick = { difficulty = TestType.DIFFICULT },
                enabled = showRightArrow,
                modifier = Modifier.alpha(if (showRightArrow) 1.0f else 0.0f)
            ) {
                Icon(Icons.Default.ArrowRight, stringResource(R.string.difficulty_increase))
            }
        }

        FormSection(
            title = {
                Text(
                    stringResource(R.string.artha),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp)
                )
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                listOf(
                    IntRollModifier(R.string.fate, rollState.fate),
                    IntRollModifier(
                        R.string.persona,
                        rollState.persona,
                        maxValue = MAX_PERSONA
                    ),
                    IntRollModifier(
                        R.string.deeds,
                        rollState.deeds,
                        maxValue = 1
                    )
                ).forEach {
                    RollCounter(
                        rollModifier = it,
                        showLabel = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        val diceModifiers = listOf(
            IntRollModifier(R.string.help, rollState.helpingDice),
            IntRollModifier(R.string.advantage, rollState.advantageDice),
            IntRollModifier(R.string.forks, rollState.forks),
            IntRollModifier(R.string.wounds, rollState.wounds)
        )
        val obstacleModifiers = listOf(
            IntRollModifier(
                nameRes = when (rollType) {
                    RollType.VERSUS -> R.string.opponent_roll
                    else -> R.string.base_obstacle
                },
                number = rollState.baseObstacle
            ),
            IntRollModifier(R.string.disadvantage, rollState.disadvantage),
            IntRollModifier(R.string.other, rollState.otherObstacle),
            BooleanRollModifier(R.string.doubled, rollState.obstacleDoubled)
        )

        val modifiers = mutableMapOf<String, List<RollModifier>>(
            stringResource(R.string.additional_dice) to diceModifiers
        )
        if (rollType != RollType.GRADUATED) {
            modifiers["Obstacle Modifiers"] = obstacleModifiers
        }

        ListSectionWithHeader(
            grouped = modifiers,
            key = { it.nameRes },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            ModifierRow(rollModifier = it)
        }
    }
}

@Composable
private fun ModifierRow(
    rollModifier: RollModifier,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .heightIn(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(rollModifier.nameRes),
            modifier = Modifier.weight(2f)
        )
        when (rollModifier) {
            is IntRollModifier -> {
                RollCounter(
                    rollModifier = rollModifier,
                    modifier = Modifier.weight(1f)
                )
            }
            is BooleanRollModifier -> {
                Switch(
                    checked = rollModifier.value.value,
                    onCheckedChange = { rollModifier.onToggle(it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RollCounter(
    rollModifier: IntRollModifier,
    modifier: Modifier = Modifier,
    showLabel: Boolean = false
) {
    val number = rollModifier.number
    FormCounter(
        modifier = modifier,
        label = if (showLabel) stringResource(rollModifier.nameRes) else null,
        value = number.value,
        labelColor = MaterialTheme.colorScheme.onSurface,
        iconColor = MaterialTheme.colorScheme.onSurface,
        onIncrement = { if (number.value < rollModifier.maxValue) number.increment() },
        onDecrement = { if (number.value > rollModifier.minValue) number.decrement() }
    )
}

@Composable
private fun NumberDisplay(
    value: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.large,
            shadowElevation = 2.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(100.dp)
            ) {
                Text(
                    value.toString(),
                    style = MaterialTheme.typography.displayMedium.copy(fontFamily = Bitter)
                )
            }
        }
        Text(
            label,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFeatureSettings = "smcp"
            )
        )
    }
}

@Preview(widthDp = 340)
@Composable
private fun RollDetailPreview() {
    val skill = createTestSkill(skillName = "Stealthy")
    val type = RollType.STANDARD
    AppTheme {
        RollDetailContent(RollState(skill, remember { mutableStateOf(type) }))
    }
}