package com.ciarasouthgate.burningwheeltesttracker.hilt

import com.ciarasouthgate.burningwheeltesttracker.preferences.AppPreferences
import com.ciarasouthgate.burningwheeltesttracker.preferences.AppPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    @Singleton
    abstract fun providePreferences(appPreferences: AppPreferencesImpl): AppPreferences
}