package com.ciarasouthgate.burningwheeltesttracker.util

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

val resources = getInstrumentation().targetContext.resources

fun getString(res: Int, vararg args: String) = resources.getString(res, *args)