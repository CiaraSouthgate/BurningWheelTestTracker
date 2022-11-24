package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

@Composable
fun TextFieldWithError(
    errorRes: Int?,
    modifier: Modifier = Modifier,
    textField: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        textField()
        errorRes?.let {
            Text(
                text = stringResource(it),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun TextFieldWithErrorPreview(
    @PreviewParameter(ErrorParameterProvider::class) error: Int?
) {
    var value by remember { mutableStateOf("") }
    AppTheme {
        TextFieldWithError(error) {
            TextField(value = value, onValueChange = { value = it })
        }
    }
}

class ErrorParameterProvider : PreviewParameterProvider<Int?> {
    override val values: Sequence<Int?> = sequenceOf(R.string.character_name_required, null)
}