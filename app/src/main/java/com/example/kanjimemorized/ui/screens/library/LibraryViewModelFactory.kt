package com.example.kanjimemorized.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.IdeogramRepository

class LibraryViewModelFactory(
    private val ideogramRepository: IdeogramRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            return LibraryViewModel(ideogramRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}