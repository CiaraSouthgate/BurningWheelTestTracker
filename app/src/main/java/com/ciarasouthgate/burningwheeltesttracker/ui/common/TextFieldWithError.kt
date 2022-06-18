package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun TextFieldWithError(
    error: String?,
    modifier: Modifier = Modifier,
    textField: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        textField()
        error?.let {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun TextFieldWithErrorPreview(
    @PreviewParameter(ErrorParameterProvider::class) error: String?
) {
    var value by remember { mutableStateOf("") }
    TestTrackerTheme {
        TextFieldWithError(error) {
            TextField(value = value, onValueChange = { value = it })
        }
    }
}

class ErrorParameterProvider : PreviewParameterProvider<String?> {
    override val values: Sequence<String?> = sequenceOf("Test error message", null)
}