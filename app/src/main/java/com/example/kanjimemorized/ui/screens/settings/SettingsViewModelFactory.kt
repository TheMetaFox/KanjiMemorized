package com.example.kanjimemorized.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class SettingsViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return modelClass.cast(SettingsViewModel(kanjiRepository)) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}