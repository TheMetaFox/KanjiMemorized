package com.example.kanjimemorized.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.ui.screens.statistics.StatisticsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        value = HomeState()
    )

    val state: StateFlow<HomeState> = _state

    fun onEvent(
        homeEvent: HomeEvent
    ) {
        when(homeEvent) {
            is HomeEvent.LoadHomeData -> {
                viewModelScope.launch {
                    var currentReviews: Int = 0
                    kanjiRepository.getKanjiList().forEach { kanji ->
                        if (kanji.durability > 0) {
                            if (kanjiRepository.getRetentionFromKanji(kanji.unicode) < 0.80f) {
                                currentReviews++
                            }
                        }
                    }

                    _state.update {
                        it.copy(
                            currentReviews = currentReviews
                        )
                    }
                }
            }
        }
    }
}