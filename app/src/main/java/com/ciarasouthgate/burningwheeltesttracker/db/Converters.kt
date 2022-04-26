package com.ciarasouthgate.burningwheeltesttracker.db

import androidx.room.TypeConverter
import com.ciarasouthgate.burningwheeltesttracker.common.Shade
import com.ciarasouthgate.burningwheeltesttracker.common.Type

class Converters {
    @TypeConverter
    fun toShade(value: Int) = enumValues<Shade>()[value]

    @TypeConverter
    fun fromShade(value: Shade) = value.ordinal

    @TypeConverter
    fun toType(value: Int) = enumValues<Type>()[value]

    @TypeConverter
    fun fromType(value: Type) = value.ordinal
}