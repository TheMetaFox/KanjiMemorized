package com.example.kanjimemorized.ui.screens.library.kanji

import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review

data class KanjiState(
    val kanji: Kanji? = null,
    val components: List<Kanji>? = listOf(),
    val reviews: List<Review> = listOf(),
    val retention: Float = 0f,
    val isShowingReviewData: Boolean = false
)
