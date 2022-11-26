package com.ciarasouthgate.burningwheeltesttracker.preferences

import android.content.Context
import com.ciarasouthgate.burningwheeltesttracker.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AppPreferences {
    var activeCharacterId: Long
}

@Singleton
class AppPreferencesImpl @Inject constructor(@ApplicationContext context: Context) :
    AppPreferences {
    private val preferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    override var activeCharacterId by preferences.long(KEY_ACTIVE_CHARACTER_ID, 0)

    companion object {
        private const val KEY_ACTIVE_CHARACTER_ID = "activeCharacterId"
    }
}