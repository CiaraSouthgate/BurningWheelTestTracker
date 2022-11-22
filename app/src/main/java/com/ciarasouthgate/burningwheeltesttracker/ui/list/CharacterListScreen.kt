package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.common.TestTrackerDialog
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material2AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.getCharacterListViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.CharacterListViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.CharacterListViewModelImpl

@Composable
fun CharacterListScreen(
    onCharacterAdded: (Long) -> Unit,
    onCharacterClicked: (Character) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: CharacterListViewModel = hiltViewModel<CharacterListViewModelImpl>()
) {
    val characters by viewModel.items.collectAsState(emptyList())

    var showCharacterDialog by remember { mutableStateOf(false) }
    var activeCharacter by remember { mutableStateOf<Character?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    ListScreen(
        title = stringResource(R.string.characters),
        onAddClicked = {
            showCharacterDialog = true
        },
        viewModel = viewModel,
        emptyText = stringResource(R.string.no_characters),
        navigationIcon = navigationIcon
    ) { paddingValues ->
        ColumnListWithDividers(
            modifier = Modifier.padding(paddingValues),
            items = characters,
            key = { it.name }
        ) { character ->
            CharacterListItem(
                character,
                onCharacterClicked,
                onEdit = {
                    activeCharacter = character
                    showCharacterDialog = true
                },
                onDelete = {
                    activeCharacter = character
                    showDeleteDialog = true
                }
            )
        }
    }
    if (showCharacterDialog) {
        AddCharacterDialog(
            onCharacterSaved = onCharacterAdded,
            onEdit = viewModel::editCharacter,
            onAdd = viewModel::addCharacter,
            onDismiss = {
                activeCharacter = null
                showCharacterDialog = false
            },
            character = activeCharacter
        )
    }
    if (showDeleteDialog) {
        activeCharacter?.let { character ->
            TestTrackerDialog(
                onDismiss = {
                    activeCharacter = null
                    showDeleteDialog = false
                },
                title = stringResource(R.string.delete_character_title, character.name),
                buttons = {
                    TextButton(onClick = {
                        activeCharacter = null
                        showDeleteDialog = false
                    }) {
                        Text(stringResource(R.string.cancel))
                    }
                    Button(onClick = {
                        activeCharacter?.let { viewModel.deleteListItem(it) }
                        activeCharacter = null
                        showDeleteDialog = false
                    }) {
                        Text(stringResource(R.string.delete))
                    }
                },
                content = {}
            )
        } ?: throw IllegalStateException("Active character must be specified for delete dialog")
    }
}

@Preview
@Composable
fun PreviewCharacterListScreen() {
    Material2AppTheme {
        CharacterListScreen(
            onCharacterAdded = {},
            onCharacterClicked = {},
            viewModel = getCharacterListViewModel()
        )
    }
}