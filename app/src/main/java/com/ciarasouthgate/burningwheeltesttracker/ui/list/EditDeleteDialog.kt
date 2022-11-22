package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerDialog


@Composable
fun EditDeleteDialog(
    entityName: String,
    onDismiss: () -> Unit,
    onConfirmAction: () -> Unit,
    state: EditDeleteDialogState
) {
    TestTrackerDialog(
        onDismiss = onDismiss,
        title = stringResource(
            when (state) {
                EditDeleteDialogState.EDIT -> R.string.edit_dialog_title
                EditDeleteDialogState.DELETE -> R.string.delete_dialog_title
            },
            entityName
        ),
        content = {},
        buttons = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    onConfirmAction()
                    onDismiss()
                }
            ) {
                Text(
                    stringResource(
                        when (state) {
                            EditDeleteDialogState.EDIT -> R.string.edit
                            EditDeleteDialogState.DELETE -> R.string.delete
                        }
                    )
                )
            }
        }
    )
}

enum class EditDeleteDialogState {
    EDIT, DELETE
}