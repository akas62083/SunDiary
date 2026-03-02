package com.akas62083.sundiary.db

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {
    @TypeConverter
    fun from(value: Set<String>): String {
        return Json.encodeToString(value)
    }
    @TypeConverter
    fun to(value: String): Set<String> {
        return Json.decodeFromString(value)
    }
}