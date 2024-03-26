package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Review(
    @PrimaryKey(autoGenerate = false)
    val date: LocalDateTime,
    val unicode: Char,
    val rating: Int
) {

}
