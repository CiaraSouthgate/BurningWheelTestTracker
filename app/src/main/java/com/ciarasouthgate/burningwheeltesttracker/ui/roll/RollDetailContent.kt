package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_PERSONA
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.roll.RollState
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Bitter
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material2AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill

@Composable
fun RollDetailContent(
    rollState: RollState,
    rollType: RollType,
    modifier: Modifier = Modifier
) {
    var showDiceModifierDialog by remember { mutableStateOf(false) }
    var showObstacleModifierDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.testing_header, rollState.skillName),
            style = MaterialTheme.typography.h4
        )
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val showObstacle = rollType != RollType.GRADUATED
            val displayModifier = Modifier.widthIn(40.dp)
            DieDisplay(
                value = rollState.diceRolled,
                label = stringResource(R.string.dice_rolled),
                modifier = displayModifier,
                expanded = showDiceModifierDialog,
                onExpandToggled = {
                    showDiceModifierDialog = !showDiceModifierDialog
                    if (showDiceModifierDialog) showObstacleModifierDialog = false
                }
            )

            if (showObstacle) {
                Text(
                    stringResource(R.string.vs),
                    style = MaterialTheme.typography.h6
                )
                DieDisplay(
                    value = rollState.obstacle,
                    label = stringResource(
                        if (rollType == RollType.STANDARD) R.string.obstacle else R.string.opponent
                    ),
                    modifier = displayModifier,
                    expanded = showObstacleModifierDialog,
                    onExpandToggled = {
                        showObstacleModifierDialog = !showObstacleModifierDialog
                        if (showObstacleModifierDialog) showDiceModifierDialog = false
                    }
                )
            }
        }

        val baseObModifier =
            IntRollModifier(stringResource(R.string.base_obstacle), rollState.baseObstacle)
        val obDoubledModifier =
            BooleanRollModifier(stringResource(R.string.doubled), rollState.obstacleDoubled)
        val personaModifier = IntRollModifier(
            stringResource(R.string.persona),
            rollState.persona,
            maxValue = MAX_PERSONA
        )
        val deedsModifier = BooleanRollModifier(stringResource(R.string.deeds), rollState.deeds)

        CollapsibleRollModifierList(
            rollModifiers = listOf(
                IntRollModifier(stringResource(R.string.forks), rollState.forks),
                IntRollModifier(stringResource(R.string.advantage), rollState.advantageDice),
                IntRollModifier(stringResource(R.string.help), rollState.helpingDice),
                personaModifier,
                deedsModifier
            ),
            expanded = showDiceModifierDialog,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        val obModifiers = when (rollType) {
            RollType.STANDARD -> listOf(
                baseObModifier,
                IntRollModifier(stringResource(R.string.disadvantage), rollState.disadvantage),
                IntRollModifier(stringResource(R.string.other), rollState.otherObstacle),
                obDoubledModifier
            )
            RollType.VERSUS -> listOf(baseObModifier, obDoubledModifier)
            RollType.GRADUATED -> emptyList()
        }

        CollapsibleRollModifierList(
            rollModifiers = obModifiers,
            expanded = showObstacleModifierDialog,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        ArthaDisplay(
            rollModifiers = listOf(
                IntRollModifier(stringResource(R.string.fate), rollState.fate),
                personaModifier,
                deedsModifier
            )
        )
    }
}

@Composable
private fun DieDisplay(
    value: Int,
    label: String,
    expanded: Boolean,
    onExpandToggled: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value.toString(),
            style = TextStyle(
                fontFamily = Bitter,
                fontSize = 60.sp,
            )
        )
        TextButton(onClick = onExpandToggled) {
            Text(
                label,
                style = MaterialTheme.typography.button.copy(
                    fontFeatureSettings = "c2sc, smcp"
                )
            )
            Icon(
                if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                null
            )
        }
    }
}

@Composable
private fun ArthaDisplay(
    rollModifiers: List<RollModifier>
) {
    Text(
        text = stringResource(R.string.artha),
        style = MaterialTheme.typography.h5
    )

    RollModifierList(rollModifiers)
}

@Preview(widthDp = 340)
@Composable
private fun RollDetailPreview() {
    val skill = createTestSkill(skillName = "Stealthy")
    Material2AppTheme {
        RollDetailContent(
            RollState(skill),
            RollType.STANDARD
        )
    }
}