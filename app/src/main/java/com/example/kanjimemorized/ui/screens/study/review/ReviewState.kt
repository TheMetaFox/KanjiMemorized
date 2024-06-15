package com.example.kanjimemorized.ui.screens.study.review

import com.example.kanjimemorized.data.entities.Kanji
import java.util.PriorityQueue

data class ReviewState(
    val kanji: Kanji? = null,
    val meanings: List<String>? = listOf(),
    val isAnswerShowing: Boolean = false,
    val isReviewAvailable: Boolean = false,
    val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
)
