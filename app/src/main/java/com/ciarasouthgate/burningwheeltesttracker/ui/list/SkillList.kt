package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.skill.SkillTestDisplay
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.ui.util.createTestCharacters
import com.ciarasouthgate.burningwheeltesttracker.ui.util.createTestSkill
import com.ciarasouthgate.burningwheeltesttracker.ui.util.createTestSkillList
import com.ciarasouthgate.burningwheeltesttracker.ui.util.createTestStatList

@Composable
fun SkillList(
    skills: List<Skill>,
    onSkillClicked: (Skill) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(skills) { skill ->
            SkillListItem(skill, onSkillClicked)
            Divider(thickness = 0.5.dp)
        }
    }
}

@Composable
fun SkillListItem(
    skill: Skill,
    onClick: (Skill) -> Unit,
    modifier: Modifier = Modifier
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
        Text(
            skill.shade.letter,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            skill.exponent.toString(),
            style = MaterialTheme.typography.titleLarge,
        )
        SkillTestDisplay(
            skill,
            modifier = Modifier.padding(start = 10.dp)
        )
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
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
        Text(
            stringResource(R.string.artha_list, skill.fateSpent, skill.personaSpent, skill.deedsSpend),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
@Preview(widthDp = 340)
fun SkillListPreview() {
    AppTheme {
        SkillList(
            createTestSkillList(3, "Character Name"), {}
        )
    }
}

@Composable
@Preview(widthDp = 340)
fun StatListPreview() {
    AppTheme {
        SkillList(skills = createTestStatList(), onSkillClicked = {})
    }
}