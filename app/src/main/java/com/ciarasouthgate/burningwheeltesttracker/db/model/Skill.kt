package com.ciarasouthgate.burningwheeltesttracker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.ciarasouthgate.burningwheeltesttracker.common.*
import com.ciarasouthgate.burningwheeltesttracker.db.Converters
import kotlin.math.ceil

@Entity(primaryKeys = ["name", "characterName"])
@TypeConverters(Converters::class)
class Skill(
    val name: String,
    val characterName: String,
    var exponent: Int,
    val type: Type = Type.SKILL,
    var shade: Shade = Shade.BLACK,

    @ColumnInfo(name = "tests_routine")
    var routineTests: Int = 0,

    @ColumnInfo(name = "tests_difficult")
    var difficultTests: Int = 0,

    @ColumnInfo(name = "tests_challenging")
    var challengingTests: Int = 0,

    @ColumnInfo(name = "spent_fate")
    var fateSpent: Int = 0,

    @ColumnInfo(name = "spent_persona")
    var personaSpent: Int = 0,

    @ColumnInfo(name = "spent_deeds")
    var deedsSpent: Int = 0,

    @ColumnInfo(name = "aristeia_available")
    var aristeiaAvailable: Boolean = false,

    @ColumnInfo(name = "aristeia_spent")
    var aristeiaUsed: Boolean = false
) {
    init {
        if (exponent < MIN_EXPONENT || exponent > MAX_EXPONENT) {
            throw IllegalArgumentException("Exponent must be between 1 and 9")
        }

        if (routineTests < 0 || difficultTests < 0 || challengingTests < 0) {
            throw IllegalArgumentException("Tests cannot be less than 0")
        }

        if (fateSpent < 0 || personaSpent < 0 || deedsSpent < 0) {
            throw IllegalArgumentException("Artha spent cannot be less than 0")
        }
    }

    private val requiredRoutineTests: Int
        get() = getRequiredTests(TestType.ROUTINE)

    private val requiredDifficultTests: Int
        get() = getRequiredTests(TestType.DIFFICULT)

    private val requiredChallengingTests: Int
        get() = getRequiredTests(TestType.CHALLENGING)

    val requiresDifficultOrChallenging: Boolean
        get() = this.type != Type.STAT && exponent < 5

    fun getCompletedTestsForType(type: TestType): Int = when (type) {
        TestType.ROUTINE -> routineTests
        TestType.DIFFICULT -> difficultTests
        TestType.CHALLENGING -> challengingTests
    }

    fun getRequiredTestsForType(type: TestType): Int = when (type) {
        TestType.ROUTINE -> requiredRoutineTests
        TestType.DIFFICULT -> requiredDifficultTests
        TestType.CHALLENGING -> requiredChallengingTests
    }

    fun addTestAndCheckUpgrade(type: TestType): Boolean {
        when (type) {
            TestType.ROUTINE -> routineTests++
            TestType.DIFFICULT -> difficultTests++
            TestType.CHALLENGING -> challengingTests++
        }

        return checkExponentAdvancement()
    }

    fun spendArthaAndCheckAdvancement(fate: Int, persona: Int, deeds: Boolean): Boolean {
        fateSpent += fate
        personaSpent += persona
        if (deeds) deedsSpent++

        return checkArthaAdvancement()
    }

    fun useAristeia() {
        if (!aristeiaAvailable) throw Exception("You can't use aristeia you haven't earned!")
        aristeiaAvailable = false
        aristeiaUsed = true
    }

    private fun checkExponentAdvancement(): Boolean {
        val requiredRoutine = routineTests >= requiredRoutineTests
        val requiredDifficult = routineTests >= requiredDifficultTests
        val requiredChallenging = routineTests >= requiredChallengingTests

        if (
            (requiredRoutine && requiredDifficult && requiredChallenging) ||
            (requiredRoutine && requiredDifficult && requiresDifficultOrChallenging) ||
            (requiredRoutine && requiredChallenging && requiresDifficultOrChallenging)
        ) {
            exponent++
            return true
        }
        return false
    }

    private fun checkArthaAdvancement(): Boolean {
        if (!aristeiaUsed && deedsSpent >= DEEDS_ARISTEIA
            && personaSpent >= PERSONA_ARISTEIA && fateSpent >= FATE_ARISTEIA) {
            aristeiaAvailable = true
        }

        if (deedsSpent >= DEEDS_EPIPHANY && personaSpent >= PERSONA_EPIPHANY
            && fateSpent >= FATE_EPIPHANY) {
            shadeShift()

            return true
        }
        return false
    }

    private fun shadeShift() {
        shade = if (shade == Shade.BLACK) Shade.GREY else Shade.WHITE

        aristeiaAvailable = false
        aristeiaUsed = false

        fateSpent = 0
        personaSpent = 0
        deedsSpent = 0
    }

    private fun getRequiredTests(testType: TestType): Int {
        if (exponent < MIN_EXPONENT || exponent > MAX_EXPONENT) {
            throw IllegalArgumentException("Invalid exponent")
        }
        return when (testType) {
            TestType.ROUTINE -> if (exponent < 5 && this.type == Type.SKILL) exponent else 0
            TestType.DIFFICULT -> ceil(exponent / 2.0).toInt()
            TestType.CHALLENGING -> when (exponent) {
                6, 7 -> 2
                8, 9 -> 3
                else -> 1
            }
        }
    }
}