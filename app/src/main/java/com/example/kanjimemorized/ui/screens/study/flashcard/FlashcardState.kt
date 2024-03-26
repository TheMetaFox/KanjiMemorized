package com.example.kanjimemorized.ui.screens.study.flashcard

import com.example.kanjimemorized.data.entities.Kanji

data class FlashcardState(
    val kanji: Kanji? = null,
    val isAnswerShowing: Boolean = false
)
