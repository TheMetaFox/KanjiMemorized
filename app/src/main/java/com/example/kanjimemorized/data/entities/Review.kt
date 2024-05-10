package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val unicode: Char,
    val rating: Int
) {
    constructor(date: String, unicode: Char, rating: Int) : this(
        id = 0, date = date, unicode = unicode, rating = rating
    )
}