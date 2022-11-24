package com.ciarasouthgate.burningwheeltesttracker.db.model

import androidx.room.*
import com.ciarasouthgate.burningwheeltesttracker.common.*
import com.ciarasouthgate.burningwheeltesttracker.db.Converters
import kotlin.math.ceil

@Entity(
    foreignKeys = [ForeignKey(
        entity = BaseCharacter::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("characterId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["characterId", "name"], unique = true)]
)
@TypeConverters(Converters::class)
class Skill(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val characterId: Long,
    val name: String,
    exponent: Int,
    val type: Type = Type.SKILL,
    var shade: Shade = Shade.BLACK,
    routineTests: Int = 0,
    difficultTests: Int = 0,
    challengingTests: Int = 0,
    fateSpent: Int = 0,
    personaSpent: Int = 0,
    deedsSpent: Int = 0,
    @ColumnInfo(name = "aristeia_available")
    var aristeiaAvailable: Boolean = false,
    @ColumnInfo(name = "aristeia_spent")
    var aristeiaUsed: Boolean = false,
    @ColumnInfo(name = "success_required")
    val successRequired: Boolean = false
) {
    var exponent = exponent
        set(value) {
            require(value >= MIN_EXPONENT)
            require(value <= MAX_EXPONENT)
            field = value
        }

    @ColumnInfo(name = "tests_routine")
    var routineTests: Int = routineTests
        set(value) {
            require(value >= 0)
            field = value
        }

    @ColumnInfo(name = "tests_difficult")
    var difficultTests: Int = difficultTests
        set(value) {
            require(value >= 0)
            field = value
        }

    @ColumnInfo(name = "tests_challenging")
    var challengingTests: Int = challengingTests
        set(value) {
            require(value >= 0)
            field = value
        }

    @ColumnInfo(name = "spent_fate")
    var fateSpent: Int = fateSpent
        set(value) {
            require(value >= 0)
            field = value
        }

    @ColumnInfo(name = "spent_persona")
    var personaSpent: Int = personaSpent
        set(value) {
            require(value >= 0)
            field = value
        }

    @ColumnInfo(name = "spent_deeds")
    var deedsSpent: Int = deedsSpent
        set(value) {
            require(value >= 0)
            field = value
        }

    init {
        require(name.isNotBlank())
        require(exponent >= MIN_EXPONENT)
        require(exponent <= MAX_EXPONENT)
        require(routineTests >= 0)
        require(difficultTests >= 0)
        require(challengingTests >= 0)
        require(fateSpent >= 0)
        require(personaSpent >= 0)
        require(deedsSpent >= 0)

        checkArthaAdvancement()
        checkExponentAdvancement()
    }

    private val requiredRoutineTests: Int
        get() = getRequiredTests(TestType.ROUTINE)

    private val requiredDifficultTests: Int
        get() = getRequiredTests(TestType.DIFFICULT)

    private val requiredChallengingTests: Int
        get() = getRequiredTests(TestType.CHALLENGING)

    val optionalTestTypes: Boolean
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
        if (persona > MAX_PERSONA) throw IllegalArgumentException(
            "Cannot spend more than $MAX_PERSONA persona on one roll"
        )

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
        if (exponent == MAX_EXPONENT) return false

        val requiredRoutine = routineTests >= requiredRoutineTests
        val requiredDifficult = difficultTests >= requiredDifficultTests
        val requiredChallenging = challengingTests >= requiredChallengingTests

        if (
            (requiredRoutine && requiredDifficult && requiredChallenging) ||
            (requiredRoutine && requiredDifficult && optionalTestTypes) ||
            (requiredRoutine && requiredChallenging && optionalTestTypes)
        ) {
            exponent++
            return true
        }
        return false
    }

    private fun checkArthaAdvancement(): Boolean {
        if (!aristeiaUsed
            && deedsSpent >= DEEDS_ARISTEIA
            && personaSpent >= PERSONA_ARISTEIA
            && fateSpent >= FATE_ARISTEIA
        ) {
            aristeiaAvailable = true
        }

        if (deedsSpent >= DEEDS_EPIPHANY
            && personaSpent >= PERSONA_EPIPHANY
            && fateSpent >= FATE_EPIPHANY
        ) {
            return if (shade == Shade.WHITE) {
                false
            } else {
                shadeShift()
                true
            }
        }
        return false
    }

    private fun shadeShift() {
        shade = if (shade == Shade.BLACK) Shade.GREY else Shade.WHITE

        aristeiaAvailable = false
        aristeiaUsed = false

        fateSpent -= FATE_EPIPHANY
        personaSpent -= PERSONA_EPIPHANY
        deedsSpent -= DEEDS_EPIPHANY
    }

    private fun getRequiredTests(testType: TestType): Int {
        return when (testType) {
            TestType.ROUTINE -> if (exponent < 5 && this.type != Type.STAT) exponent else 0
            TestType.DIFFICULT -> ceil(exponent / 2.0).toInt()
            TestType.CHALLENGING -> when (exponent) {
                6, 7 -> 2
                8, 9 -> 3
                else -> 1
            }
        }
    }
}