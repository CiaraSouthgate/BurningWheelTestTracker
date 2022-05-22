package com.ciarasouthgate.burningwheeltesttracker.db.model

import com.ciarasouthgate.burningwheeltesttracker.common.MAX_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.common.MIN_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.TestType.*
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.util.createRandomTestSkill
import com.ciarasouthgate.burningwheeltesttracker.util.createTestSkill
import org.junit.Assert.*
import org.junit.Test

internal class SkillTest {
    private val requiredStatTests = mapOf(
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

    private val requiredSkillTests = mapOf(
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

    @Test
    fun test_getRequiresDifficultOrChallenging() {
        val difficultOrChallengingMap = mapOf(
            1 to true,
            2 to true,
            3 to true,
            4 to true,
            5 to false,
            6 to false,
            7 to false,
            8 to false,
            9 to false
        )
        for (exp in 1 until MAX_EXPONENT) {
            val skill = createTestSkill(exponent = exp)
            val stat = createTestSkill(exponent = exp, type = Type.STAT)
            assertEquals(
                "Incorrect result for skill with exponent $exp",
                difficultOrChallengingMap[exp], skill.optionalTestTypes
            )
            assertFalse(
                "Incorrect result for stat with exponent $exp",
                stat.optionalTestTypes
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
        for (exp in 1 until MAX_EXPONENT) {
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
        for (exp in MIN_EXPONENT until MAX_EXPONENT) {
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
    fun test_addTestAndCheckUpgrade_noRoutine() {
        val exponent = 3
        val skill = createTestSkill(
            exponent = exponent,
            routine = 1,
            difficult = 2,
            challenging = 1
        )
        assertNoAdvancement(skill, exponent, ROUTINE)
    }

    @Test
    fun test_addTestAndCheckUpgrade_routineNoDifficultNoChallenging() {
        val exponent = 1
        val skill = createTestSkill(
            exponent = exponent,
            routine = 0,
            difficult = 0,
            challenging = 0
        )
        assertNoAdvancement(skill, exponent, ROUTINE)
    }

    @Test
    fun test_addTestAndCheckUpgrade_difficultNoChallengingNonOptional() {
        val exponent = 5
        val skill = createTestSkill(
            exponent = exponent,
            routine = 0,
            difficult = 2,
            challenging = 0
        )
        assertNoAdvancement(skill, exponent, DIFFICULT)
    }

    @Test
    fun test_addTestAndCheckUpgrade_routineNoDifficultChallengingNonOptional() {
        val exponent = 5
        val skill = createTestSkill(
            exponent = exponent,
            routine = 0,
            difficult = 0,
            challenging = 0
        )
        assertNoAdvancement(skill, exponent, CHALLENGING)
    }

    @Test
    fun test_addTestAndCheckUpgrade_routineDifficultChallenging() {
        val exponent = 5
        val skill = createTestSkill(
            exponent = exponent,
            difficult = 2,
            challenging = 1
        )

        assertAdvancement(skill, exponent, DIFFICULT)
    }

    @Test
    fun test_addTestAndCheckUpgrade_routineDifficultNoChallengingOptional() {
        val exponent = 1
        val skill = createTestSkill(
            exponent = exponent,
            routine = 0,
            difficult = 1,
            challenging = 0
        )

        assertAdvancement(skill, exponent, ROUTINE)
    }

    @Test
    fun test_addTestAndCheckUpgrade_routineNoDifficultChallengingOptional() {
        val exponent = 1
        val skill = createTestSkill(
            exponent = exponent,
            routine = 1,
            difficult = 0,
            challenging = 0
        )

        assertAdvancement(skill, exponent, CHALLENGING)
    }

    @Test
    fun test_addTestAndCheckUpgrade_maxExponentRespected() {
        val exponent = MAX_EXPONENT
        val skill = createTestSkill(
            exponent = exponent,
            routine = 0,
            difficult = 5,
            challenging = 5
        )

        TestType.values().forEach { testType ->
            assertNoAdvancement(skill, exponent, testType)
        }
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
            createTestSkill(exponent = 11)
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

    @Test
    fun test_updateSkill_invalidValues() {
        val skill = createTestSkill()

        assertThrows("Negative exponent accepted", IllegalArgumentException::class.java) {
            skill.exponent = -1
        }

        assertThrows("Too high exponent accepted", IllegalArgumentException::class.java) {
            skill.exponent = 11
        }

        assertThrows("Zero exponent accepted", IllegalArgumentException::class.java) {
            skill.exponent = 0
        }

        assertThrows("Negative routine tests accepted", IllegalArgumentException::class.java) {
            skill.routineTests = -1
        }

        assertThrows("Negative difficult tests accepted", IllegalArgumentException::class.java) {
            skill.difficultTests = -1
        }

        assertThrows("Negative challenging tests accepted", IllegalArgumentException::class.java) {
            skill.challengingTests = -1
        }

        assertThrows("Negative fate accepted", IllegalArgumentException::class.java) {
            skill.fateSpent = -1
        }

        assertThrows("Negative persona accepted", IllegalArgumentException::class.java) {
            skill.personaSpent = -1
        }

        assertThrows("Negative deeds accepted", IllegalArgumentException::class.java) {
            skill.deedsSpent = -1
        }
    }

    @Test
    fun test_updateSkill_validValues() {
        val skill = createTestSkill()

        skill.exponent = 1
        assertEquals(1, skill.exponent)

        skill.routineTests = 1
        assertEquals(1, skill.routineTests)

        skill.difficultTests = 1
        assertEquals(1, skill.difficultTests)

        skill.challengingTests = 1
        assertEquals(1, skill.challengingTests)

        skill.fateSpent = 1
        assertEquals(1, skill.fateSpent)

        skill.personaSpent = 1
        assertEquals(1, skill.personaSpent)

        skill.deedsSpent = 1
        assertEquals(1, skill.deedsSpent)
    }

    private fun assertNoAdvancement(skill: Skill, startExponent: Int, testType: TestType) {
        val testBefore = skill.getTests(testType)
        val upgraded = skill.addTestAndCheckUpgrade(testType)

        assertFalse("Skill upgraded unexpectedly on $testType test", upgraded)
        assertEquals(
            "Skill upgraded unexpectedly on $testType test",
            startExponent,
            skill.exponent
        )
        assertEquals(
            "Test count failed to update expectedly for $testType test",
            testBefore + 1,
            skill.getTests(testType)
        )
    }

    private fun assertAdvancement(skill: Skill, startExponent: Int, testType: TestType) {
        val testBefore = skill.getTests(testType)
        val upgraded = skill.addTestAndCheckUpgrade(testType)

        assertTrue("Skill did not upgrade on $testType test", upgraded)
        assertEquals(
            "Skill did not upgrade on $testType test",
            startExponent + 1,
            skill.exponent
        )
        assertEquals(
            "Test count failed to update expectedly for $testType test",
            testBefore + 1,
            skill.getTests(testType)
        )
    }

    private fun Skill.getTests(type: TestType) = when (type) {
        ROUTINE -> routineTests
        DIFFICULT -> difficultTests
        CHALLENGING -> challengingTests
    }
}