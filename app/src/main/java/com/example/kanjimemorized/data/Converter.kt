package com.example.kanjimemorized.data

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun charToString(unicode: Char): String {
        return unicode.toString()
    }
    @TypeConverter
    fun stringToChar(unicode: String): Char {
        return Integer.parseInt(unicode, 16).toChar()
    }

    @TypeConverter
    fun stringListToString(meanings: List<String>): String {
        return meanings.toString()
    }
    @TypeConverter
    fun stringToStringList(meanings: String): List<String> {
        return meanings.split(",")
    }
}