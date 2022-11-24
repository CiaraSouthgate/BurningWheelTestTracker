package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.material.ContentAlpha
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FormCounter(
    label: String?,
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
            label?.let {
                ComponentLabel(
                    it,
                    color = if (labelColor == Color.Unspecified) iconColor else labelColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
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