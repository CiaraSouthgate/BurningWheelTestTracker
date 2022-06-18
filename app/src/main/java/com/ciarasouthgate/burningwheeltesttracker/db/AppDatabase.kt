package com.ciarasouthgate.burningwheeltesttracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ciarasouthgate.burningwheeltesttracker.db.dao.CharacterDao
import com.ciarasouthgate.burningwheeltesttracker.db.dao.SkillDao
import com.ciarasouthgate.burningwheeltesttracker.db.model.BaseCharacter
import com.ciarasouthgate.burningwheeltesttracker.db.model.Skill

@Database(
    entities = [
        BaseCharacter::class,
        Skill::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun skillDao(): SkillDao
}

private var INSTANCE: AppDatabase? = null

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        return INSTANCE ?: Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "skills"
        ).build().also { INSTANCE = it }
    }
}