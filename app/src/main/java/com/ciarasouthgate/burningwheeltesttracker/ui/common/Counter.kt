package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrement) {
            Icon(Icons.Default.Remove, stringResource(R.string.decrement))
        }
        Text(
            value.toString(),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.widthIn(25.dp),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        IconButton(onClick = onIncrement) {
            Icon(Icons.Default.Add, stringResource(R.string.increment))
        }
    }
}

@Composable
fun CounterWithLabel(
    labelText: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Counter(value, onIncrement, onDecrement)
        Text(
            labelText,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    TestTrackerTheme {
        var value by remember { mutableStateOf(0) }
        CounterWithLabel(
            labelText = "Fate",
            value = value,
            onIncrement = { value++ },
            onDecrement = { value-- }
        )
    }
}