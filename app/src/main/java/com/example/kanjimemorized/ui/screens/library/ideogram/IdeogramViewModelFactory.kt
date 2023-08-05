package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.IdeogramRepository

class IdeogramViewModelFactory(
    private val ideogramRepository: IdeogramRepository
): ViewModelProvider.Factory {
    override fun < T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IdeogramViewModel::class.java)) {
            return IdeogramViewModel(ideogramRepository) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}