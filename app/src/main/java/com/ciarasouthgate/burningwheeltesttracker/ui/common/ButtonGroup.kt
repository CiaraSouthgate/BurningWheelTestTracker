package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

private const val CORNER_RADIUS = 50

@Composable
fun OutlinedButtonGroup(
    buttons: List<ButtonData>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-1).dp)
    ) {
        buttons.forEachIndexed { index, button ->
            val startShape = if (index == 0) CORNER_RADIUS else 0
            val endShape = if (index == buttons.size - 1) CORNER_RADIUS else 0
            val textColor = MaterialTheme.colorScheme.onSurface
            val backgroundColor = if (button.isActive) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            } else {
                MaterialTheme.colorScheme.surface
            }
            ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                OutlinedButton(
                    onClick = button.onClick,
                    shape = RoundedCornerShape(
                        topStartPercent = startShape,
                        bottomStartPercent = startShape,
                        topEndPercent = endShape,
                        bottomEndPercent = endShape
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = textColor,
                        containerColor = backgroundColor
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    button.contents()
                }
            }
        }
    }
}

data class ButtonData(
    var isActive: Boolean,
    val onClick: () -> Unit,
    val contents: @Composable () -> Unit
)

@Preview
@Composable
private fun ButtonGroupPreview() {
    var activeButton by remember { mutableStateOf(0) }
    AppTheme {
        OutlinedButtonGroup(
            List(3) { i ->
                ButtonData(
                    activeButton == i,
                    { activeButton = i }
                ) { Text("Button ${i + 1}") }
            }
        )
    }
}