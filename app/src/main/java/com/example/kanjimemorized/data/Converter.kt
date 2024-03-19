package com.example.kanjimemorized.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDate.parse

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
    fun localDateToString(phaseLocalDate: LocalDate): String {
        return(phaseLocalDate.toString())
    }
    @TypeConverter
    fun stringToLocalDate(phaseString: String): LocalDate {
        return(parse(phaseString))
    }
}
