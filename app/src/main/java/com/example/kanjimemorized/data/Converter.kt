package com.example.kanjimemorized.data

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun charToString(unicodeChar: Char): String {
        return unicodeChar.toString()
    }
    @TypeConverter
    fun stringToChar(unicodeString: String): Char {
        return Integer.parseInt(unicodeString, 16).toChar()
    }

    @TypeConverter
    fun stringListToString(meaningsStringList: List<String>): String {
        return meaningsStringList.toString().replace("[", "").replace("]","")
    }
    @TypeConverter
    fun stringToStringList(meaningsString: String): List<String> {
        return meaningsString.split(",")
    }
}