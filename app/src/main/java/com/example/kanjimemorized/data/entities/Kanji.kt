package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Kanji(
    @PrimaryKey(autoGenerate = false)
    val unicode: Char, // unicode of the ideogram character
    val meanings: List<String>, // a few of it's most relevant meanings
    val strokes: Int, // used as an indicator of ideogram complexity
    val durability: Float, // the strength of a memory; it's resistance to being forgotten
) {
    constructor(unicode: Char, meanings: List<String>, strokes: Int) : this(
        unicode, meanings, strokes, durability = 0f
    )
}
