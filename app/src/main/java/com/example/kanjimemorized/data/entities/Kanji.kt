package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kanji(
    @PrimaryKey(autoGenerate = false)
    val unicode: Char, // unicode of the ideogram character
    val strokes: Int, // used as an indicator of ideogram complexity
    val durability: Float, // the strength of a memory; it's resistance to being forgotten
    val ease: Float // the easiness of the kanji; it's what durability multiplies with
) {
    constructor(unicode: Char, strokes: Int) : this(
        unicode, strokes, durability = 0f, ease = 2.5f
    )
}
