package com.example.kanjimemorized.ui.screens.library.kanji

import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import java.time.LocalDateTime

data class KanjiState(
    val kanji: Kanji? = null,
    val components: List<Kanji>? = listOf(),
    val reviews: List<Review> = listOf(),
    val retention: Float = 0f,
    val latestDate: LocalDateTime? = null,
    val isShowingReviewData: Boolean = false
)
