package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Bitter
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material3AppTheme

@Composable
fun Counter(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            Icons.Default.Remove,
            stringResource(R.string.decrement),
            tint = iconColor,
            modifier = Modifier
                .clickable { onDecrement() }
                .weight(1f)
        )
        Text(
            value.toString(),
            style = textStyle,
            modifier = Modifier
                .widthIn(30.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Icon(
            Icons.Default.Add,
            stringResource(R.string.increment),
            tint = iconColor,
            modifier = Modifier
                .clickable { onIncrement() }
                .weight(1f)
        )
    }
}

@Composable
fun CounterWithLabel(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    CounterWithLabel(
        { ComponentLabel(label) },
        value,
        onIncrement,
        onDecrement,
        modifier,
        iconColor,
        alignment,
        labelPosition,
        textStyle
    )
}

@Composable
fun CounterWithLabel(
    label: @Composable () -> Unit,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    LabelledComponent(
        label = label,
        modifier = modifier,
        alignment = alignment,
        labelPosition = labelPosition
    ) {
        Counter(
            value,
            onIncrement,
            onDecrement,
            iconColor = iconColor,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    Material3AppTheme {
        var value by remember { mutableStateOf(0) }
        CounterWithLabel(
            label = "Label",
            value = value,
            onIncrement = { value++ },
            onDecrement = { value-- }
        )
    }
}