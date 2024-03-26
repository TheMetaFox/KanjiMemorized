package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.entities.Kanji
import java.time.LocalDateTime

data class LibraryState(
    val kanji: List<Kanji> = emptyList(),
    val date: List<LocalDateTime> = emptyList(),
    val unicode: String = "",
    val meanings: String = "",
    val strokes: String = "",
    val retention: List<Float> = emptyList(),
    val retention1: Float = 0f,
    val filterNonStudyable: Boolean = false,
    val sortType: SortType = SortType.UNICODE,

    val kanjiRetention: List<Pair<Kanji, Float>> = emptyList()
)
