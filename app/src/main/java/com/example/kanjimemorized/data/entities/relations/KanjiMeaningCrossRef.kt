package com.example.kanjimemorized.data.entities.relations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KanjiMeaningCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val unicode: Char,
    val meaning: String
)