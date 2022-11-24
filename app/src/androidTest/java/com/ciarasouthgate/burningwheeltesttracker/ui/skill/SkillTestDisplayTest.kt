package com.ciarasouthgate.burningwheeltesttracker.ui.skill

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.ciarasouthgate.burningwheeltesttracker.common.MAX_TESTS_NEEDED
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.util.createRandomTestSkill
import com.ciarasouthgate.burningwheeltesttracker.util.getString
import org.junit.Rule
import org.junit.Test

class SkillTestDisplayTest {
    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun testElementsDisplayed() {
        val skill = createRandomTestSkill(type = Type.SKILL)

        testRule.setContent {
            SkillTestDisplay(skill)
        }

        TestType.values().forEach { type ->
            val label = getString(type.nameRes)[0].toString()

            testRule.onNodeWithTag(type.name)
                .assertExists("Missing row for $type")
                .assertIsDisplayed()

            testRule.onNodeWithText(label)
                .assertExists("Missing label for $type")
                .assertIsDisplayed()
        }

        testRule.onAllNodesWithTag(SKILL_DISPLAY_ICON_TAG)
            .assertCountEquals(MAX_TESTS_NEEDED * TestType.values().size)
    }
}