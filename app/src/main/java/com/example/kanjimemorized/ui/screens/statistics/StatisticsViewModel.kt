package com.example.kanjimemorized.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatisticsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true
    )
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        value = StatisticsState()
    )

    val state: StateFlow<StatisticsState> = _state

    fun onEvent(
        statisticsEvent: StatisticsEvent
    ) {
        when (statisticsEvent) {

            is StatisticsEvent.LoadStatisticsData -> {
                viewModelScope.launch {
                    var unknown = 0
                    var known = 0
                    var mastered = 0
                    kanjiRepository.getKanjiList().forEach {kanji ->
                        if (kanji.durability == 0f) {
                            unknown++
                        } else if (kanji.durability < 5f) {
                            known++
                        } else {
                            mastered++
                        }
                    }

                    _state.update {
                        it.copy(
                            unlocked = kanjiRepository.getUnlockedKanjiList().size,
                            known = known,
                            mastered = mastered,
                            unknown = unknown
                        )
                    }
                    _isLoading.value = false
                }
            }
        }
    }
}