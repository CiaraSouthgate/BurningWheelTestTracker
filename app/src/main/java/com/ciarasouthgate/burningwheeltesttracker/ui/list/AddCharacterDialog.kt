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
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material3AppTheme

@Composable
fun AddCharacterDialog(
    onCharacterSaved: (Long) -> Unit,
    onDismiss: () -> Unit,
    onAdd: suspend (String) -> Long?,
    onEdit: suspend (Character) -> Boolean,
    character: Character? = null
) {
    var characterName by remember { mutableStateOf(character?.name.orEmpty()) }
    var isError by remember { mutableStateOf(false) }
    var isSaveAttempted by remember { mutableStateOf(false) }
    val isEditing by derivedStateOf { character != null }

    TestTrackerDialog(
        title = stringResource(if (isEditing) R.string.edit_character else R.string.add_character),
        onDismiss = onDismiss,
        content = {
            OutlinedTextField(
                value = characterName,
                onValueChange = {
                    characterName = it
                    isError = false
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                isError = isError,
                placeholder = { Text(stringResource(R.string.name)) },
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                stringResource(R.string.name_exists),
                style = MaterialTheme.typography.bodySmall,
                color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent
            )
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
                        isError = true
                    } else if (!isError) {
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
            val characterId = character?.let {
                val updatedCharacter = character.copy(
                    character = character.character.copy(name = characterName)
                )
                if (onEdit(updatedCharacter)) it.id else null
            } ?: onAdd(characterName)
            characterId?.let { onCharacterSaved(it) } ?: run { isError = true }
            isSaveAttempted = false
        }
    }
}

@Preview
@Composable
fun AddCharacterDialogPreview() {
    Material3AppTheme {
        AddCharacterDialog(
            onCharacterSaved = {},
            onAdd = { null },
            onEdit = { true },
            onDismiss = {}
        )
    }
}