package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun CharacterListScreen(
    onCharacterAdded: (String) -> Unit,
    onCharacterClicked: (Character) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    var showAddDialog by remember { mutableStateOf(false) }

    ListScreen(
        title = stringResource(R.string.characters),
        onAddClicked = { showAddDialog = true },
        viewModel = viewModel,
        emptyText = stringResource(R.string.no_characters),
        navigationIcon = navigationIcon
    ) { paddingValues ->
        CharacterList(
            characters = viewModel.items,
            onCharacterClicked = onCharacterClicked,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
    if (showAddDialog) {
        AddCharacterDialog(
            viewModel,
            onCharacterAdded = onCharacterAdded,
            onDismiss = { showAddDialog = false }
        )
    }
}

@Preview
@Composable
fun PreviewCharacterListScreen() {
    TestTrackerTheme {
        CharacterListScreen(
            onCharacterAdded = {},
            onCharacterClicked = {}
        )
    }
}