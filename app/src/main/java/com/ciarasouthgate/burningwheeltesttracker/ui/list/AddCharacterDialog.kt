package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerDialog
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TextFieldWithError
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

@Composable
fun AddCharacterDialog(
    onCharacterSaved: (Long) -> Unit,
    onDismiss: () -> Unit,
    onAdd: suspend (String) -> Long?,
    onEdit: suspend (Character) -> Boolean,
    character: Character? = null
) {
    var characterName by remember { mutableStateOf(character?.name.orEmpty()) }
    var characterNameError by remember { mutableStateOf<Int?>(null) }
    var isSaveAttempted by remember { mutableStateOf(false) }
    val isEditing by derivedStateOf { character != null }

    TestTrackerDialog(
        title = stringResource(if (isEditing) R.string.edit_character else R.string.add_character),
        onDismiss = onDismiss,
        content = {
            TextFieldWithError(
                errorRes = characterNameError,
                modifier = Modifier.align(Alignment.Center)
            ) {
                OutlinedTextField(
                    value = characterName,
                    onValueChange = {
                        characterName = it
                        characterNameError = null
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    isError = characterNameError != null,
                    placeholder = { Text(stringResource(R.string.name)) }
                )
            }
        },
        buttons = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.cancel).uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                onClick = {
                    if (characterName.isBlank()) {
                        characterNameError = R.string.character_name_required
                    } else {
                        isSaveAttempted = true
                    }
                }
            ) {
                Text(
                    stringResource(if (isEditing) R.string.save else R.string.add).uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    )

    if (isSaveAttempted) {
        LaunchedEffect(Unit) {
            val trimmedName = characterName.trim()
            val characterId = character?.let {
                val updatedCharacter = character.copy(
                    character = character.character.copy(name = trimmedName)
                )
                if (onEdit(updatedCharacter)) it.id else null
            } ?: onAdd(trimmedName)
            characterId?.let { onCharacterSaved(it) } ?: run {
                characterNameError = R.string.character_name_exists
            }
            isSaveAttempted = false
            onDismiss()
        }
    }
}

@Preview
@Composable
fun AddCharacterDialogPreview() {
    AppTheme {
        AddCharacterDialog(
            onCharacterSaved = {},
            onAdd = { null },
            onEdit = { true },
            onDismiss = {}
        )
    }
}