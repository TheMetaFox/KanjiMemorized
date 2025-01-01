package com.example.kanjimemorized.ui.screens.home.flashcard

import com.example.kanjimemorized.data.entities.Kanji
import java.util.PriorityQueue

data class FlashcardState(
    val studyType: StudyType = StudyType.NEW,
    val kanji: Kanji? = null,
    val meanings: List<String> = listOf(),
    val isAnswerShowing: Boolean = false,
    val isReviewAvailable: Boolean = false,
    val queue: PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first }),
    val isLoading: Boolean = true,
    val lastRating: Int = 0,
    val isAnimationPlaying: Boolean = false
)