package com.example.kanjimemorized.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true
    )
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
                    Log.i("HomeViewModel.kt", "Started loading initial data...")
                    var currentReviewCount = 0
                    Log.i("HomeViewModel.kt", "1...")
                    kanjiRepository.getKanjiList().forEach { kanji ->
                        if (kanji.durability > 0) {
                            if (kanjiRepository.getRetentionFromKanji(kanji.unicode) < 0.80f) {
                                currentReviewCount++
                            }
                        }
                    }
                    Log.i("HomeViewModel.kt", "2...")
                    _state.update {
                        Log.i("HomeViewModel.kt", "3...")
                        it.copy(
                            currentReviewCount = currentReviewCount,
                            currentNewCount = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue.toInt() - kanjiRepository.getEarliestDateCountFromToday()
                        )
                    }
                    Log.i("HomeViewModel.kt", "4...")
                    _isLoading.value = false
                    Log.i("HomeViewModel.kt", "Finished loading initial data...")
                }
            }
        }
    }
}