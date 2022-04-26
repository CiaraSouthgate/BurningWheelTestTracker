package com.ciarasouthgate.burningwheeltesttracker.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class BaseCharacter(
    @PrimaryKey
    val name: String
)

data class Character(
    @Embedded val character: BaseCharacter,
    @Relation(
        parentColumn = "name",
        entityColumn = "characterName"
    )
    val skills: List<Skill>
) {
    val name: String get() = character.name

    constructor(name: String, skills: List<Skill> = emptyList()) : this(BaseCharacter(name), skills)
}