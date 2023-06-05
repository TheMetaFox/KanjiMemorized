package com.example.kanjimemorized.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ideogram(
    @PrimaryKey(autoGenerate = false)
    val unicode: Char,
    val meanings: List<String>,
    val strokes: String,
    val decompositions: String?,
    val retention: Float,
    val coercivity: Float,
)
