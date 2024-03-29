package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.common.SwipeToDelete
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
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
            SwipeToDelete(
                onEdit = {
                    activeCharacter = character
                    showCharacterDialog = true
                },
                onDelete = {
                    activeCharacter = character
                    showDeleteDialog = true
                }
            ) {
                CharacterListItem(
                    character,
                    onCharacterClicked
                )
            }
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
            EditDeleteDialog(
                entityName = character.name,
                onDismiss = {
                    activeCharacter = null
                    showDeleteDialog = false
                },
                onConfirmAction = { viewModel.deleteListItem(character) },
                state = EditDeleteDialogState.DELETE
            )
        } ?: throw IllegalStateException("Active character must be specified for delete dialog")
    }
}

@Preview
@Composable
fun PreviewCharacterListScreen() {
    AppTheme {
        CharacterListScreen(
            onCharacterAdded = {},
            onCharacterClicked = {},
            viewModel = getCharacterListViewModel()
        )
    }
}