package com.ciarasouthgate.burningwheeltesttracker.common

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PositiveInt(initialValue: Int) : ReadWriteProperty<Any, Int> {
    var value: Int = initialValue
    override fun getValue(thisRef: Any, property: KProperty<*>): Int = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        require(value >= 0)
        this.value = value
    }
}

fun positiveInt(value: Int) = PositiveInt(value)