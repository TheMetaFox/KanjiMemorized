package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.IdeogramRepository

class FlashcardViewModelFactory(
    private val ideogramRepository: IdeogramRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            return FlashcardViewModel(ideogramRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}