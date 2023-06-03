package com.example.kanjimemorized

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ideogram(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var meanings: String,
    val strokes: Int,
    var decompositions: String?,
    val retention: Float,
    val coercivity: Float,

)
