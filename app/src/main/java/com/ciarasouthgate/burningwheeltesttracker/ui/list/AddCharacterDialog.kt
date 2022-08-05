package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun AddCharacterDialog(
    viewModel: CharacterListViewModel,
    onCharacterAdded: (String) -> Unit,
    onDismiss: () -> Unit,
    initialValue: String = ""
) {
    var characterName by remember { mutableStateOf(initialValue) }
    var isError by remember { mutableStateOf(false) }
    var isAddAttempted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(5.dp)) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    stringResource(R.string.add_character),
                    style = MaterialTheme.typography.h6
                )

                TextField(
                    value = characterName,
                    onValueChange = {
                        characterName = it
                        isError = false
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    isError = isError,
                    placeholder = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    stringResource(R.string.name_exists),
                    style = MaterialTheme.typography.caption,
                    color = if (isError) MaterialTheme.colors.error else Color.Transparent
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            stringResource(R.string.cancel),
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (characterName.isBlank()) {
                                isError = true
                            } else if (!isError) {
                                isAddAttempted = true
                            }
                        }
                    ) {
                        Text(
                            stringResource(R.string.add),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }

    if (isAddAttempted) {
        LaunchedEffect(Unit) {
            if (viewModel.addCharacter(characterName)) {
                onCharacterAdded(characterName)
            } else {
                isError = true
            }
            isAddAttempted = false
        }
    }
}

@Preview
@Composable
fun AddCharacterDialogPreview() {
    TestTrackerTheme {
        AddCharacterDialog(
            hiltViewModel(),
            onCharacterAdded = {},
            onDismiss = {}
        )
    }
}