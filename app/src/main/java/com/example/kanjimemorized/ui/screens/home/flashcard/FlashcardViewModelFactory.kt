package com.example.kanjimemorized.ui.screens.home.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class FlashcardViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            return FlashcardViewModel(kanjiRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}