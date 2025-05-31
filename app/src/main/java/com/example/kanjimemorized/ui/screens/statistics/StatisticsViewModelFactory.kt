package com.example.kanjimemorized.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.data.KanjiRepository

class StatisticsViewModelFactory(
    private val kanjiRepository: KanjiRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return modelClass.cast(StatisticsViewModel(kanjiRepository)) as T
        }
        throw IllegalArgumentException("ViewModel class was not found.")
    }
}