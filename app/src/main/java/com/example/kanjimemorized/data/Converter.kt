package com.example.kanjimemorized.data

import androidx.room.TypeConverter
import java.time.LocalDateTime.parse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converter {

    @TypeConverter
    fun stringListToString(meaningsStringList: List<String>): String {
        return meaningsStringList.toString().replace("[", "").replace("]","")
    }
    @TypeConverter
    fun stringToStringList(meaningsString: String): List<String> {
        return meaningsString.split(",")
    }

    @TypeConverter
    fun characterListToString(decompositionsCharacterList: List<Char>?): String {
        if (decompositionsCharacterList == null) {
            return ""
        }
        if (decompositionsCharacterList.isEmpty()) {
            return ""
        }
        return decompositionsCharacterList.toString().replace("[", "").replace("]","")
    }
    @TypeConverter
    fun stringToCharacterList(decompositionsCharacterString: String): List<Char> {
        return decompositionsCharacterString.toList()
    }

    @TypeConverter
    fun localDateTimeToString(phaseLocalDate: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        phaseLocalDate.format(formatter)
        return(phaseLocalDate.toString())
    }
    @TypeConverter
    fun stringToLocalDateTime(phaseString: String): LocalDateTime {
        return(parse(phaseString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
    }
}
