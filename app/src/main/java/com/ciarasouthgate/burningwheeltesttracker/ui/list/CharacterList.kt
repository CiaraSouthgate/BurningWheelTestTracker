package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Black50Alpha
import com.ciarasouthgate.burningwheeltesttracker.ui.util.createTestCharacters


@Composable
fun CharacterList(
    characters: List<Character>,
    onCharacterClicked: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(characters) { character ->
            CharacterListItem(character, onCharacterClicked)
            Divider(thickness = 0.5.dp)
        }
    }
}


@Composable
fun CharacterListItem(
    character: Character,
    onClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(character) }
            .padding(5.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = resources.getQuantityString(
                R.plurals.num_skills,
                character.skills.size,
                character.skills.size
            ),
            color = Black50Alpha
        )
    }
}

@Preview(widthDp = 340)
@Composable
fun CharacterListItemPreview() {
    AppTheme {
        CharacterList(createTestCharacters(3), {})
    }
}