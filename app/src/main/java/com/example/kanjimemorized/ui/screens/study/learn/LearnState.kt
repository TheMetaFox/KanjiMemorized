package com.example.kanjimemorized.ui.screens.study.learn

import com.example.kanjimemorized.data.entities.Kanji
import java.util.PriorityQueue

data class LearnState(
    val kanji: Kanji? = null,
    val meanings: List<String>? = listOf(),
    val isAnswerShowing: Boolean = false,
    val isReviewAvailable: Boolean = false,
    val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })

)
