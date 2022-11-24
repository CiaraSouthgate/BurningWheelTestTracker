package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun LabelledComponent(
    label: String,
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    labelPosition: LabelPosition = LabelPosition.TOP,
    content: @Composable ColumnScope.() -> Unit
) {
    LabelledComponent(
        label = { ComponentLabel(label) },
        modifier = modifier,
        alignment = alignment,
        labelPosition = labelPosition,
        content = content
    )
}

@Composable
fun LabelledComponent(
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    labelPosition: LabelPosition = LabelPosition.TOP,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = alignment
    ) {
        if (labelPosition == LabelPosition.TOP) label()
        content()
        if (labelPosition == LabelPosition.BOTTOM) label()
    }
}

@Composable
fun ComponentLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.titleSmall
) {
    Text(
        text,
        style = style,
        color = color,
        modifier = modifier
    )
}

enum class LabelPosition {
    TOP, BOTTOM
}