package com.example.kanjimemorized.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class LibraryViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            return modelClass.cast(LibraryViewModel(kanjiRepository)) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}