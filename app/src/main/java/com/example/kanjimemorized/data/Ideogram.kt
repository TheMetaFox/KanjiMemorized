package com.example.kanjimemorized.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@Entity
data class Ideogram(
    @PrimaryKey(autoGenerate = false)
    val unicode: Char, // unicode of the ideogram character
    val meanings: List<String>, // a few of it's most relevant meanings
    val strokes: Int, // used as an indicator of ideogram complexity
    val decompositions: List<Char>?, // ideograms that exist within other ideograms
    val retention: Float, // the probability of remembering an idea at a specific point in time
    val coercivity: Float, // the degree to which a memory is retained (the forgetting curve)
    val phase: LocalDate, // the date in of the last review in ... form
    val period: Int, // number of days between the previous review and the next one
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(unicode: Char, meanings: List<String>, strokes: Int) : this(
        unicode, meanings, strokes, decompositions = null, retention = 0f, coercivity = 0f, phase = LocalDate.now(), period = 0
    )
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(unicode: Char, meanings: List<String>, strokes: Int, decompositions: List<Char>?) : this(
        unicode, meanings, strokes, decompositions, retention = 0f, coercivity = 0f, phase = LocalDate.now(), period = 0
    )
}
