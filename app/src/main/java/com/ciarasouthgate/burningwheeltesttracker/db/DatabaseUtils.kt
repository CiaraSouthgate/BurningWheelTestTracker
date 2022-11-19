package com.ciarasouthgate.burningwheeltesttracker.db

import android.database.sqlite.SQLiteConstraintException

suspend fun addItem(addMethod: suspend () -> Long?): Long? = try {
    addMethod()
} catch (e: SQLiteConstraintException) {
    null
}

suspend fun editItem(editMethod: suspend () -> Unit): Boolean = try {
    editMethod()
    true
} catch (e: SQLiteConstraintException) {
    false
}