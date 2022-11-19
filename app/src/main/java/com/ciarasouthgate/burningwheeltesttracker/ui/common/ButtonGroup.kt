package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

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
            val startShape = if (index == 0) 4.dp else 0.dp
            val endShape = if (index == buttons.size - 1) 4.dp else 0.dp
            val textColor = MaterialTheme.colors.onSurface.copy(
                if (button.isActive) ContentAlpha.high else ContentAlpha.medium
            )
            val backgroundColor = if (button.isActive) {
                MaterialTheme.colors.primary.copy(alpha = 0.25f)
            } else {
                MaterialTheme.colors.surface
            }
            OutlinedButton(
                onClick = button.onClick,
                shape = RoundedCornerShape(
                    topStart = startShape,
                    bottomStart = startShape,
                    topEnd = endShape,
                    bottomEnd = endShape
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = textColor,
                    backgroundColor = backgroundColor
                )
            ) {
                button.contents()
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