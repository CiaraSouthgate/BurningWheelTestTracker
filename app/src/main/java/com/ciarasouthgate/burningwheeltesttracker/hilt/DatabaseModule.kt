package com.ciarasouthgate.burningwheeltesttracker.hilt

import android.content.Context
import androidx.room.Room
import com.ciarasouthgate.burningwheeltesttracker.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "burning-wheel-test-tracker-db"
    ).build()
}