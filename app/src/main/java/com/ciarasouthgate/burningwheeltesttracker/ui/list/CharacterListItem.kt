package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Character
import com.ciarasouthgate.burningwheeltesttracker.ui.common.SwipeToDelete
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Black50Alpha

@Composable
fun CharacterListItem(
    character: Character,
    onClick: (Character) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    SwipeToDelete(
        onDelete = onDelete,
        onEdit = onEdit,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(character) }
                .background(color = MaterialTheme.colors.background)
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
}