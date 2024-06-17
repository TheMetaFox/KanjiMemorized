package com.example.kanjimemorized.ui.screens.study.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class LearnViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearnViewModel::class.java)) {
            return LearnViewModel(kanjiRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}