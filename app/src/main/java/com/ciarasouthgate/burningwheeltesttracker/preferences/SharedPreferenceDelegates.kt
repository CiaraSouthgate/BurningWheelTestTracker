package com.ciarasouthgate.burningwheeltesttracker.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceDelegate<T>(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
    private val getter: SharedPreferences.(String, T) -> T
) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return preferences.getter(key, defaultValue)
    }

    @SuppressLint("CommitPrefEdits")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        preferences.edit()
            .setter(key, value)
            .apply()
    }
}

fun SharedPreferences.long(key: String, defaultValue: Long) =
    PreferenceDelegate(
        this,
        key,
        defaultValue,
        setter = SharedPreferences.Editor::putLong,
        getter = SharedPreferences::getLong
    )