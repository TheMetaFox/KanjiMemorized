package com.example.kanjimemorized.data.entities.relations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KanjiComponentCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kanjiUnicode: Char,
    val componentUnicode: Char
)