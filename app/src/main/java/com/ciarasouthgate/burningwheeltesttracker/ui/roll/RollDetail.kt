package com.ciarasouthgate.burningwheeltesttracker.ui.roll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciarasouthgate.burningwheeltesttracker.common.RollType
import com.ciarasouthgate.burningwheeltesttracker.roll.RollState
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill

@Composable
fun RollDetail(rollState: RollState) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val rollType by derivedStateOf { RollType.values()[selectedTabIndex] }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            RollType.values().forEachIndexed { index, rollType ->
                Tab(
                    text = { Text(rollType.name, fontSize = 12.sp) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        RollDetailContent(
            rollState,
            rollType,
            Modifier.padding(top = 10.dp)
        )
    }
}

@Preview(widthDp = 340)
@Composable
fun RollScreenPreview() {
    val skill = createTestSkill()
    TestTrackerTheme {
        RollDetail(RollState(skill))
    }
}