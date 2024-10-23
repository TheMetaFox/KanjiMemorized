package com.example.kanjimemorized.ui.screens.study.review

sealed interface ReviewEvent {
    data object InitializeQueue: ReviewEvent
    data object FlipFlashcard: ReviewEvent
    data object GetRandomFlashcard: ReviewEvent
    data object WrongCard: ReviewEvent
    data object CorrectCard: ReviewEvent
    data object EasyCard: ReviewEvent
}