package com.ciarasouthgate.burningwheeltesttracker.db.model

import com.ciarasouthgate.burningwheeltesttracker.common.MAX_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.TestType.*
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.util.createRandomTestSkill
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill
import junit.framework.Assert.*
import org.junit.Assert.assertThrows
import org.junit.Test

internal class SkillTest {
    @Test
    fun test_getRequiresDifficultOrChallenging() {
        for (exp in 1..MAX_EXPONENT) {
            val skill = createTestSkill(exponent = exp)
            val stat = createTestSkill(exponent = exp, type = Type.STAT)
            if (exp < 5) {
                assertTrue(
                    "Incorrect result for skill with exponent $exp",
                    skill.requiresDifficultOrChallenging
                )
            } else {
                assertFalse(
                    "Incorrect result for skill with exponent $exp",
                    skill.requiresDifficultOrChallenging
                )
            }
            assertFalse(
                "Incorrect result for stat with exponent $exp",
                stat.requiresDifficultOrChallenging
            )
        }
    }

    @Test
    fun test_getCompletedTestsForType() {
        val skill = createRandomTestSkill()
        assertEquals(skill.routineTests, skill.getCompletedTestsForType(ROUTINE))
        assertEquals(skill.difficultTests, skill.getCompletedTestsForType(DIFFICULT))
        assertEquals(skill.challengingTests, skill.getCompletedTestsForType(CHALLENGING))
    }

    @Test
    fun test_getRequiredTestsForType_skill() {
        val requiredSkillTests = mapOf(
            1 to listOf(1, 1, 1),
            2 to listOf(2, 1, 1),
            3 to listOf(3, 2, 1),
            4 to listOf(4, 2, 1),
            5 to listOf(0, 3, 1),
            6 to listOf(0, 3, 2),
            7 to listOf(0, 4, 2),
            8 to listOf(0, 4, 3),
            9 to listOf(0, 5, 3)
        )

        for (exp in 1..MAX_EXPONENT) {
            val skill = createTestSkill(exponent = exp)
            TestType.values().forEachIndexed { index, testType ->
                assertEquals(
                    "Unexpected result for exponent $exp, test type ${testType.name}",
                    requiredSkillTests[exp]!![index],
                    skill.getRequiredTestsForType(testType)
                )
            }
        }
    }

    @Test
    fun test_getRequiredTestsForType_stat() {
        val requiredStatTests = mapOf(
            1 to listOf(0, 1, 1),
            2 to listOf(0, 1, 1),
            3 to listOf(0, 2, 1),
            4 to listOf(0, 2, 1),
            5 to listOf(0, 3, 1),
            6 to listOf(0, 3, 2),
            7 to listOf(0, 4, 2),
            8 to listOf(0, 4, 3),
            9 to listOf(0, 5, 3)
        )

        for (exp in 1..MAX_EXPONENT) {
            val stat = createTestSkill(exponent = exp, type = Type.STAT)
            TestType.values().forEachIndexed { index, testType ->
                assertEquals(
                    requiredStatTests[exp]!![index],
                    stat.getRequiredTestsForType(testType)
                )
            }
        }
    }

    @Test
    fun test_useAristeia_available() {
        val skill = createTestSkill().apply { aristeiaAvailable = true }

        skill.useAristeia()
        assertTrue(skill.aristeiaUsed)
        assertFalse(skill.aristeiaAvailable)
    }

    @Test
    fun test_useAristeia_unavailable() {
        val skill = createTestSkill().apply { aristeiaAvailable = false }

        assertThrows(Exception::class.java, skill::useAristeia)
    }

    @Test
    fun test_addTestAndCheckUpgrade() {

    }

    @Test
    fun test_createSkill_invalidValues() {
        assertThrows("Empty skill name accepted", IllegalArgumentException::class.java) {
            createTestSkill(skillName = "")
        }
        assertThrows("Blank skill name accepted", IllegalArgumentException::class.java) {
            createTestSkill(skillName = " ")
        }
        assertThrows("Empty character name accepted", IllegalArgumentException::class.java) {
            createTestSkill(characterName = "")
        }
        assertThrows("Blank character name accepted", IllegalArgumentException::class.java) {
            createTestSkill(characterName = " ")
        }
        assertThrows("Negative exponent accepted", IllegalArgumentException::class.java) {
            createTestSkill(exponent = -1)
        }
        assertThrows("Too high exponent accepted", IllegalArgumentException::class.java) {
            createTestSkill(exponent = 10)
        }

        assertThrows("Zero exponent accepted", IllegalArgumentException::class.java) {
            createTestSkill(exponent = 0)
        }

        assertThrows("Negative routine tests accepted", IllegalArgumentException::class.java) {
            createTestSkill(routine = -1)
        }

        assertThrows("Negative difficult tests accepted", IllegalArgumentException::class.java) {
            createTestSkill(difficult = -1)
        }

        assertThrows("Negative challenging tests accepted", IllegalArgumentException::class.java) {
            createTestSkill(challenging = -1)
        }

        assertThrows("Negative fate accepted", IllegalArgumentException::class.java) {
            createTestSkill(fateSpent = -1)
        }

        assertThrows("Negative persona accepted", IllegalArgumentException::class.java) {
            createTestSkill(personaSpent = -1)
        }

        assertThrows("Negative deeds accepted", IllegalArgumentException::class.java) {
            createTestSkill(deedsSpent = -1)
        }
    }
}