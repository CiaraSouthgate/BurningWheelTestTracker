package com.ciarasouthgate.burningwheeltesttracker.common

enum class Shade(val prettyName: String) {
    BLACK("Black"),
    GREY("Grey"),
    WHITE("White")
}

enum class Type {
    STAT, SKILL
}

enum class TestType(val prettyName: String) {
    ROUTINE("Routine"),
    DIFFICULT("Difficult"),
    CHALLENGING("Challenging")
}

enum class ArthaType(val prettyName: String) {
    FATE("Fate"),
    PERSONA("Persona"),
    DEEDS("Deeds")
}

enum class RollType {
    STANDARD, VERSUS, GRADUATED
}