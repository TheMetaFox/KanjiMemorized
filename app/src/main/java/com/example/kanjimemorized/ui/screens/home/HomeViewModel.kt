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
                    var currentReviewCount = 0
                    val currentNewCount = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue.toInt() - kanjiRepository.getEarliestDateCountFromToday()
                    kanjiRepository.getKanjiList().forEach { kanji ->
                        if (kanji.durability > 0.1f) {
                            Log.i("HomeViewModel.kt", "Kanji: ${kanji.unicode}\nDurability: ${kanji.durability}")
                            if (kanjiRepository.getRetentionFromKanji(kanji.unicode) < kanjiRepository.getSettingsFromCode("retention_threshold").setValue.toFloat()/100f) {
                                Log.i("HomeViewModel.kt", "${kanji.unicode} is ready to review...")
                                currentReviewCount++
                            }
                        }
                    }
                    _state.update {
                        it.copy(
                            currentReviewCount = currentReviewCount,
                            currentNewCount = if (currentNewCount >= 0) currentNewCount else 0
                        )
                    }
                    _isLoading.value = false
                }
            }
        }
    }
}