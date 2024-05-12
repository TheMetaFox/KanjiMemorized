package com.example.kanjimemorized.ui.screens.study.review

import com.example.kanjimemorized.data.entities.Kanji

data class ReviewState(
    val kanji: Kanji? = null,
    val meanings: List<String> = listOf(),
    val isAnswerShowing: Boolean = false
)
