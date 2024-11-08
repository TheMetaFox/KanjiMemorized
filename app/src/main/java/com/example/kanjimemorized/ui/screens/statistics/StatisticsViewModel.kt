package com.example.kanjimemorized.ui.screens.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Kanji
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
                    kanjiRepository.getKanjiList().forEach { kanji: Kanji ->
                        if (kanji.durability == 0f) {
                            unknown++
                        } else if (kanji.durability < 5f) {
                            known++
                        } else {
                            mastered++
                        }
                    }
                    val forecasts: List<Float> = kanjiRepository.getKnownKanjiList().map {
                        kanji: Kanji -> kanjiRepository.getForecastFromKanji(kanji.unicode)
                    }
                    Log.i("StatisticsViewModel.kt", "Forecasts: $forecasts")

                    val dayForecastsMap: MutableMap<Int, Int> = mutableMapOf()
                    forecasts.forEach { forecast ->
                        val key = if (forecast >= 0) (forecast/1440).toInt()+1 else 1
                        dayForecastsMap[key] = (dayForecastsMap.getOrDefault(key = key, defaultValue = 0))+1
                    }
                    Log.i("StatisticsViewModel.kt", "Forecast Map: $dayForecastsMap")


                    _state.update {
                        it.copy(
                            unlocked = kanjiRepository.getUnlockedKanjiList().size,
                            known = known,
                            mastered = mastered,
                            unknown = unknown,
                            kanjiCountMap = mapOf("Unknown" to unknown, "Known" to known, "Mastered" to mastered),
                            dayForecastsMap = dayForecastsMap
                        )
                    }
                    _isLoading.value = false
                }
            }
            is StatisticsEvent.SetBarGraphSpan -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            barGraphSpan = statisticsEvent.barGraphSpan
                        )
                    }
                    Log.i("StatisticsViewModel.kt", "Bar graph span set to ${_state.value.barGraphSpan}...")

                }
            }
        }
    }
}