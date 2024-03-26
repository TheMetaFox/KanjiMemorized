package com.example.kanjimemorized.ui.screens.library.kanji

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class KanjiViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun < T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KanjiViewModel::class.java)) {
            return KanjiViewModel(kanjiRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}