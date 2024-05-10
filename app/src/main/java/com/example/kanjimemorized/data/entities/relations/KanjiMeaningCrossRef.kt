package com.example.kanjimemorized.data.entities.relations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KanjiMeaningCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val unicode: Char,
    val meaning: String
) {
    constructor(unicode: Char, meaning: String) : this(
        id = 0, unicode = unicode, meaning = meaning
    )
}
