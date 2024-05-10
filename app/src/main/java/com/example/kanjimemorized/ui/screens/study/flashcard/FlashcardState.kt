package com.example.kanjimemorized.ui.screens.study.flashcard

import com.example.kanjimemorized.data.entities.Kanji

data class FlashcardState(
    val kanji: Kanji? = null,
    val meanings: List<String> = listOf(),
    val isAnswerShowing: Boolean = false
)
