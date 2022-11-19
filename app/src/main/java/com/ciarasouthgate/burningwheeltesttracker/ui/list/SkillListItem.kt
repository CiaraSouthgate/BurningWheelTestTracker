package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.common.SwipeToDelete
import com.ciarasouthgate.burningwheeltesttracker.ui.skill.SkillTestDisplay
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Alegreya
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createRandomTestSkill

@Composable
fun SkillListItem(
    skill: Skill,
    onClick: (Skill) -> Unit,
    onEdit: (Skill) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeToDelete(
        onEdit = { onEdit(skill) },
        onDelete = onDelete
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clickable { onClick(skill) }
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkillStatView(
                skill,
                modifier = Modifier.weight(1f)
            )
            ProvideTextStyle(value = MaterialTheme.typography.h6.copy(fontFamily = Alegreya)) {
                Text(
                    skill.shade.prettyName[0].toString(),
                    fontWeight = FontWeight.Bold
                )
                Text(skill.exponent.toString())
            }
            SkillTestDisplay(
                skill,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun SkillStatView(
    skill: Skill,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                skill.name,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
            )
        }
        Text(
            stringResource(
                R.string.artha_list,
                skill.fateSpent,
                skill.personaSpent,
                skill.deedsSpent
            ),
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview
@Composable
fun SkillListItemPreview() {
    val skill = createRandomTestSkill()
    AppTheme {
        SkillListItem(skill = skill, onClick = {}, onEdit = {}, onDelete = {})
    }
}