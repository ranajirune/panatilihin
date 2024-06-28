package com.raineru.panatilihin.data

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromString(value: String): List<Long> {
        return if (value == "") emptyList() else value.split(",").map { it.toLong() }
    }
}