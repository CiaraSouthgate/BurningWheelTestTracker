package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.common.SwipeToDelete
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Black50Alpha
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createTestCharacters


@Composable
fun CharacterList(
    characters: List<Character>,
    viewModel: CharacterListViewModel,
    onCharacterClicked: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(characters) { character ->
            CharacterListItem(character, viewModel, onCharacterClicked)
            Divider(thickness = 0.5.dp)
        }
    }
}


@Composable
fun CharacterListItem(
    character: Character,
    viewModel: CharacterListViewModel,
    onClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    var openEditDialog by remember { mutableStateOf(false) }
    var deleteSnackbarVisible by remember { mutableStateOf(false) }

    SwipeToDelete(
        onDelete = { deleteSnackbarVisible = true },
        onEdit = { openEditDialog = true },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(character) }
                .padding(vertical = 5.dp, horizontal = 15.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = resources.getQuantityString(
                    R.plurals.num_skills,
                    character.skills.size,
                    character.skills.size
                ),
                style = MaterialTheme.typography.body2,
                color = Black50Alpha
            )
        }
    }

    if (openEditDialog) {
        AddCharacterDialog(
            viewModel = viewModel,
            onCharacterAdded = { },
            onDismiss = { openEditDialog = false }
        )
    }
}

@Preview(widthDp = 340)
@Composable
fun CharacterListItemPreview() {
    TestTrackerTheme {
        CharacterList(createTestCharacters(3), hiltViewModel(), {})
    }
}