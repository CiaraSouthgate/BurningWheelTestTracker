package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_TESTS_NEEDED
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Alegreya
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.Material2AppTheme

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
    val textStyle = TextStyle(
        fontFamily = Alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.4.sp
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(type.nameRes)[0].toString(),
            style = textStyle,
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
    Material2AppTheme {
        SkillTestDisplay(
            Skill(
                name = "Test Skill",
                characterId = 0,
                exponent = 4,
                type = Type.SKILL,
                routineTests = 2,
                difficultTests = 0
            )
        )
    }
}