package com.example.kanjimemorized.ui.screens.study.flashcard

import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramRepository

data class FlashcardState(
    val ideogram: Ideogram? = null,
    val isAnswerShowing: Boolean = false
)
