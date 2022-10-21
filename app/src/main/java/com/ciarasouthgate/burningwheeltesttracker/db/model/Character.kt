package com.ciarasouthgate.burningwheeltesttracker.db.model

import androidx.room.*

@Entity(
    tableName = "Character",
    indices = [Index(value = arrayOf("name"), unique = true)]
)
data class BaseCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)

data class Character(
    @Embedded val character: BaseCharacter,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val skills: List<Skill>
) {
    val name: String get() = character.name
    val id: Long get() = character.id

    constructor(name: String, skills: List<Skill> = emptyList()) : this(
        BaseCharacter(name = name),
        skills
    )
}