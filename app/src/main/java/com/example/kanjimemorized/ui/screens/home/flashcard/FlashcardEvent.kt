package com.example.kanjimemorized.ui.screens.home.flashcard

sealed interface FlashcardEvent {
    data class SetStudyType(val studyType: StudyType): FlashcardEvent
    data object InitializeQueue: FlashcardEvent
    data object RefreshQueue: FlashcardEvent
    data object FlipFlashcard: FlashcardEvent
    data object GetRandomFlashcard: FlashcardEvent
    data class ProcessCard(val rating: Rating): FlashcardEvent
    data object PlayAnimation: FlashcardEvent
}