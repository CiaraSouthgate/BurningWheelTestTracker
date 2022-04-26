package com.ciarasouthgate.burningwheeltesttracker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ciarasouthgate.burningwheeltesttracker.db.Converters
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.TestType
import com.ciarasouthgate.burningwheeltesttracker.common.Type
import com.ciarasouthgate.burningwheeltesttracker.util.MAX_EXPONENT
import com.ciarasouthgate.burningwheeltesttracker.util.MIN_EXPONENT
import kotlin.math.ceil

@Entity(primaryKeys = ["name", "characterName"])
@TypeConverters(Converters::class)
class Skill(
    val name: String,
    val characterName: String,
    val type: Type,
    val exponent: Int,
    val shade: Shade = Shade.BLACK,

    @ColumnInfo(name = "tests_routine")
    val routineTests: Int = 0,

    @ColumnInfo(name = "tests_difficult")
    val difficultTests: Int = 0,

    @ColumnInfo(name = "tests_challenging")
    val challengingTests: Int = 0,

    @ColumnInfo(name = "spent_fate")
    val fateSpent: Int = 0,

    @ColumnInfo(name = "spent_persona")
    val personaSpent: Int = 0,

    @ColumnInfo(name = "spent_deeds")
    val deedsSpend: Int = 0,
) {
    val requiredRoutineTests: Int
        get() = getRequiredTests(TestType.ROUTINE, exponent)

    val requiredDifficultTests: Int
        get() = getRequiredTests(TestType.DIFFICULT, exponent)

    val requiredChallengingTests: Int
        get() = getRequiredTests(TestType.CHALLENGING, exponent)

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

    private fun getRequiredTests(type: TestType, exponent: Int): Int {
        if (exponent < MIN_EXPONENT || exponent > MAX_EXPONENT) {
            throw IllegalArgumentException("Invalid exponent")
        }
        return when (type) {
            TestType.ROUTINE -> if (exponent < 5) exponent else 0
            TestType.DIFFICULT -> ceil(exponent / 2.0).toInt()
            TestType.CHALLENGING -> when (exponent) {
                6, 7 -> 2
                8, 9 -> 3
                else -> 1
            }
        }
    }
}