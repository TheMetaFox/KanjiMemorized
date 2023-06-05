package com.example.kanjimemorized.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ideogram(
    @PrimaryKey(autoGenerate = false)
    val unicode: String,
    var meanings: String,
    val strokes: Int,
    var decompositions: String?,
    val retention: Float,
    val coercivity: Float,
)
