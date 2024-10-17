package com.example.kanjimemorized.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatisticsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        value = StatisticsState()
    )

    val state: StateFlow<StatisticsState> = _state

    fun onEvent(
        statisticsEvent: StatisticsEvent
    ) {
        when (statisticsEvent) {

            is StatisticsEvent.LoadStatistics -> {
                viewModelScope.launch {
                    var unlocked = 0
                    kanjiRepository.getKanjiList().forEach { kanji ->
                        var isLocked = false
                        kanjiRepository.getKanjiComponentsFromKanji(kanji.unicode).forEach { component ->
                            if (kanjiRepository.getRetentionFromKanji(component.unicode) <= .80f) {
                                isLocked = true
                            }
                        }
                        if (!isLocked) {
                            unlocked++
                        }
                    }

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
                            unlocked = unlocked,
                            known = known,
                            mastered = mastered,
                            unknown = unknown
                        )
                    }

                }
            }
            else -> {}
        }
    }
}