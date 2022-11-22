package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TestTrackerDialog(
    onDismiss: () -> Unit,
    title: String,
    content: @Composable BoxScope.() -> Unit,
    buttons: @Composable RowScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(5.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Box(modifier = Modifier.padding(vertical = 8.dp)) {
                    content()
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    buttons()
                }
            }
        }
    }
}