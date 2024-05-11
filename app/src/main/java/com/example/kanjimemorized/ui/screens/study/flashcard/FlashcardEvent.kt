package com.example.kanjimemorized.ui.screens.study.flashcard

sealed interface FlashcardEvent {
    data object FlipFlashcard: FlashcardEvent
    data object GetRandomFlashcard: FlashcardEvent
    data object WrongCard: FlashcardEvent
    data object CorrectCard: FlashcardEvent
    data object EasyCard: FlashcardEvent
}