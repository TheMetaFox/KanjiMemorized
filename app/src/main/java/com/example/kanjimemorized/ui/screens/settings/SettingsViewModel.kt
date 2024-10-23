package com.example.kanjimemorized.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Settings
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

class SettingsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {
    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(
        value = SettingsState()
    )

    val state: StateFlow<SettingsState> = _state

    fun onEvent(
        settingsEvent: SettingsEvent
    ) {
        when(settingsEvent) {
            is SettingsEvent.UpdateTextField -> {
                if (settingsEvent.field == "dailyNewKanji")
                _state.update {
                    it.copy(
                        dailyNewKanjiField = settingsEvent.text
                    )
                }
            }
            SettingsEvent.LoadSettingsData -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            dailyNewKanji = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue,
                        )
                    }
                }
            }
            SettingsEvent.ApplySettings -> {
                viewModelScope.launch {
                    kanjiRepository.updateSettings(code = "daily_new_kanji", setValue = state.value.dailyNewKanjiField)
                    _state.update {
                        it.copy(
                            dailyNewKanji = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue
                        )
                    }

                }
            }
            SettingsEvent.ApplyDefaultSettings -> {
                viewModelScope.launch {
                    kanjiRepository.updateSettings(code = "daily_new_kanji", setValue = "5")
                    _state.update {
                        it.copy(
                            dailyNewKanji = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue
                        )
                    }

                }
            }

        }
    }
}