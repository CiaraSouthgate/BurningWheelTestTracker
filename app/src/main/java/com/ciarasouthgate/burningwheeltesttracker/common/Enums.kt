package com.ciarasouthgate.burningwheeltesttracker.common

enum class Shade(val letter: String) {
    BLACK("B"),
    GREY("G"),
    WHITE("W")
}

enum class Type {
    STAT, SKILL
}

enum class TestType(val letter: String) {
    ROUTINE("R"),
    DIFFICULT("D"),
    CHALLENGING("C")
}

enum class ArthaType(val letter: String) {
    FATE("F"),
    PERSONA("P"),
    DEEDS("D")
}