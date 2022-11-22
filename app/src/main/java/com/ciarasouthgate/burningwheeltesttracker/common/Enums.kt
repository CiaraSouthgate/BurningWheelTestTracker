package com.ciarasouthgate.burningwheeltesttracker.common

import com.ciarasouthgate.burningwheeltesttracker.R

enum class Shade(val nameRes: Int) {
    BLACK(R.string.black),
    GREY(R.string.grey),
    WHITE(R.string.white)
}

enum class Type(val nameRes: Int) {
    SKILL(R.string.skill),
    STAT(R.string.stat),
    ATTRIBUTE(R.string.attribute)
}

enum class TestType(val nameRes: Int) {
    ROUTINE(R.string.routine),
    DIFFICULT(R.string.difficult),
    CHALLENGING(R.string.challenging)
}

enum class ArthaType(val nameRes: Int) {
    FATE(R.string.fate),
    PERSONA(R.string.persona),
    DEEDS(R.string.deeds)
}

enum class RollType {
    STANDARD, VERSUS, GRADUATED
}