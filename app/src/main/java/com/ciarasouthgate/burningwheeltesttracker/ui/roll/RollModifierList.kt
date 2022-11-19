package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.common.Counter
import com.ciarasouthgate.burningwheeltesttracker.ui.common.toggle
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

@Composable
fun RollModifierList(
    rollModifiers: List<RollModifier>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        rollModifiers.forEachIndexed { index, mod ->
            ModifierRow(mod.name, index != rollModifiers.size - 1) {
                when (mod) {
                    is BooleanRollModifier -> {
                        Switch(
                            checked = mod.value.value,
                            onCheckedChange = { mod.value.toggle() }
                        )
                    }
                    is IntRollModifier -> {
                        Counter(
                            value = mod.number.value,
                            onIncrement = mod.onIncrement,
                            onDecrement = mod.onDecrement
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CollapsibleRollModifierList(
    rollModifiers: List<RollModifier>,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + expandIn(
            expandFrom = Alignment.TopCenter,
            initialSize = { IntSize(it.width, 0) }
        ),
        exit = fadeOut() + shrinkOut(
            shrinkTowards = Alignment.TopCenter,
            targetSize = { IntSize(it.width, 0) }
        )
    ) {
        RollModifierList(rollModifiers, modifier)
    }
}

@Composable
fun ModifierRow(
    name: String,
    showDivider: Boolean,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            name,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        content()
    }
    if (showDivider) Divider()
}

@Preview
@Composable
fun RollModifierListPreview() {
    val helpingDice = remember { mutableStateOf(0) }
    val advantageDice = remember { mutableStateOf(0) }
    val doubled = remember { mutableStateOf(false) }

    val modifiers = listOf(
        IntRollModifier("Helping Dice", helpingDice),
        IntRollModifier("Advantage Dice", advantageDice),
        BooleanRollModifier("Doubled", doubled)
    )

    AppTheme {
        RollModifierList(rollModifiers = modifiers)
    }
}