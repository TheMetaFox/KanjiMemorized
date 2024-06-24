package com.example.kanjimemorized.ui.screens.statistics

import androidx.lifecycle.ViewModel
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatisticsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        value = StatisticsState()
    )

    val state: StateFlow<StatisticsState> = _state

    fun onEvent(
        statisticsEvent: StatisticsEvent
    ) {
        when (statisticsEvent) {

            else -> {}
        }
    }
}