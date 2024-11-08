package com.example.kanjimemorized.ui.screens.library.kanji

import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import java.time.LocalDateTime

data class KanjiState(
    val kanji: Kanji = Kanji(19968.toChar(), 1),
    val meaning: List<String> = listOf(),
    val retention: Float = 0f,
    val components: List<Kanji> = listOf(),
    val componentMeaning: List<List<String>> = listOf(listOf()),
    val componentsLatestDates: List<LocalDateTime?> = listOf(),
    val reviews: List<Review> = listOf(),
    val isShowingReviewData: Boolean = false
)
