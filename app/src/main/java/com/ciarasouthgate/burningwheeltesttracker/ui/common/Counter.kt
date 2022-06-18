package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun Counter(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    textStyle: TextStyle = MaterialTheme.typography.h6
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
            modifier = Modifier.clickable { onDecrement() }
        )
        Text(
            value.toString(),
            style = textStyle,
            modifier = Modifier.widthIn(30.dp),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Icon(
            Icons.Default.Add,
            stringResource(R.string.increment),
            tint = iconColor,
            modifier = Modifier.clickable { onIncrement() }
        )
    }
}

@Composable
fun CounterWithLabel(
    labelText: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
    textStyle: TextStyle = MaterialTheme.typography.h6
) {
    CounterWithLabel(
        { CounterLabel(labelText) },
        value,
        onIncrement,
        onDecrement,
        modifier,
        iconColor,
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
    labelPosition: LabelPosition = LabelPosition.BOTTOM,
    textStyle: TextStyle = MaterialTheme.typography.h6
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (labelPosition == LabelPosition.TOP) label()
        Counter(
            value,
            onIncrement,
            onDecrement,
            iconColor = iconColor,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth()
        )
        if (labelPosition == LabelPosition.BOTTOM) label()
    }
}

enum class LabelPosition {
    TOP, BOTTOM
}

@Composable
fun CounterLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.subtitle2
) {
    Text(
        text,
        style = style,
        color = color,
        modifier = modifier
    )
}

@Preview
@Composable
fun CounterPreview() {
    TestTrackerTheme {
        var value by remember { mutableStateOf(0) }
        CounterWithLabel(
            labelText = "Label",
            value = value,
            onIncrement = { value++ },
            onDecrement = { value-- }
        )
    }
}