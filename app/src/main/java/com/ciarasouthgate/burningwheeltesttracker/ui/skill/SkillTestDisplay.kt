package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_TESTS_NEEDED
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun SkillTestDisplay(
    skill: Skill,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (skill.type == Type.SKILL) {
            SkillTestRow(TestType.ROUTINE, skill)
        }
        SkillTestRow(TestType.DIFFICULT, skill)
        SkillTestRow(TestType.CHALLENGING, skill)
    }
}

@Composable
fun SkillTestRow(type: TestType, skill: Skill) {
    val iconSize = 15.dp
    val numComplete = skill.getCompletedTestsForType(type)
    val numNeeded = skill.getRequiredTestsForType(type)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            type.prettyName[0].toString(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .width(iconSize)
                .alignByBaseline(),
            textAlign = TextAlign.Center
        )
        for (i in 1..MAX_TESTS_NEEDED) {
            val icon = if (i > numNeeded) {
                Icons.Default.Circle
            } else if (i <= numComplete) {
                Icons.Default.TaskAlt
            } else if (type != TestType.ROUTINE && skill.optionalTestTypes) {
                null
            } else {
                Icons.Default.RadioButtonUnchecked
            }
            val painter = icon?.let {
                rememberVectorPainter(it)
            } ?: painterResource(R.drawable.ic_half_filled_circle)
            Icon(
                painter,
                null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
@Preview
fun SkillTestDisplayPreview() {
    TestTrackerTheme {
        SkillTestDisplay(
            Skill(
                "Test Skill",
                "CharacterName",
                4,
                Type.SKILL,
                routineTests = 2,
                difficultTests = 0
            )
        )
    }
}