package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val datetime: String,
    val unicode: Char,
    val rating: Int
) {
    constructor(date: String, unicode: Char, rating: Int) : this(
        id = 0, datetime = date, unicode = unicode, rating = rating
    )
}