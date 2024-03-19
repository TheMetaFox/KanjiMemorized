package com.example.kanjimemorized.ui.screens.study.flashcard

sealed interface FlashcardEvent {
    object FlipFlashcard: FlashcardEvent
    object GetRandomFlashcard: FlashcardEvent
    object WrongCard: FlashcardEvent
    object CorrectCard: FlashcardEvent
    object EasyCard: FlashcardEvent
}