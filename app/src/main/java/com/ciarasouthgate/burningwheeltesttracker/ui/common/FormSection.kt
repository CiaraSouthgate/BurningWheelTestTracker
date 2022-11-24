package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun FormSection(
    modifier: Modifier = Modifier,
    titleRes: Int? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        titleRes?.let {
            Text(
                stringResource(titleRes),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        Box(modifier = Modifier.padding(horizontal = 5.dp)) {
            content()
        }
    }
}