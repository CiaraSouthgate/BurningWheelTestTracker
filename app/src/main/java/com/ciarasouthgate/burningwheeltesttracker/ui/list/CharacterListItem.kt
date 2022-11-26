package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Black50Alpha

@Composable
fun CharacterListItem(
    character: Character,
    onClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val skillCount = character.skills.count { it.type == Type.SKILL }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(character) }
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 5.dp, horizontal = 15.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = resources.getQuantityString(
                R.plurals.num_skills,
                skillCount,
                skillCount
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = Black50Alpha
        )

    }
}