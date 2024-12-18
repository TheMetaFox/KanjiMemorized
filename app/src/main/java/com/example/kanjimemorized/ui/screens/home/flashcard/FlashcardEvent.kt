package com.example.kanjimemorized.ui.screens.home.flashcard

sealed interface FlashcardEvent {
    data class SetStudyType(val studyType: StudyType): FlashcardEvent
    data object InitializeQueue: FlashcardEvent
    data object RefreshQueue: FlashcardEvent
    data object FlipFlashcard: FlashcardEvent
    data object GetRandomFlashcard: FlashcardEvent
    data object WrongCard: FlashcardEvent
    data object CorrectCard: FlashcardEvent
    data object EasyCard: FlashcardEvent
    data object PlayAnimation: FlashcardEvent
}