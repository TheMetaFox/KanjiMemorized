package com.example.kanjimemorized.ui.screens.study.learn

sealed interface LearnEvent {
    data object InitializeQueue: LearnEvent
    data object FlipFlashcard: LearnEvent
    data object GetRandomFlashcard: LearnEvent
    data object WrongCard: LearnEvent
    data object CorrectCard: LearnEvent
    data object EasyCard: LearnEvent
}