package com.example.kanjimemorized.ui.screens.settings

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true
    )
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(
        value = SettingsState()
    )

    val state: StateFlow<SettingsState> = _state

    fun onEvent(
        settingsEvent: SettingsEvent
    ) {
        when (settingsEvent) {
            is SettingsEvent.UpdateTextField -> {
                when (settingsEvent.field) {
                    "dailyNewKanji" -> {
                        _state.update {
                            it.copy(
                                dailyNewKanjiField = settingsEvent.text
                            )
                        }
                    }
                    "initialEase" -> {
                        _state.update {
                            it.copy(
                                initialEaseField = settingsEvent.text
                            )
                        }
                    }
                }
            }

            SettingsEvent.LoadSettingsData -> {
                Log.i("SettingsViewModel.kt", "Started loading initial data...")
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            dailyNewKanji = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue,
                            initialEase = kanjiRepository.getSettingsFromCode(code = "initial_ease").setValue
                        )
                    }
                    _isLoading.value = false
                    Log.i("SettingsViewModel.kt", "Finished loading initial data...")
                }
            }

            SettingsEvent.ApplySettings -> {
                viewModelScope.launch {
                    val dailyNewKanjiField: String = state.value.dailyNewKanjiField
                    if (dailyNewKanjiField.isNotBlank() && dailyNewKanjiField.isDigitsOnly()) {
                        kanjiRepository.updateSettings(code = "daily_new_kanji", setValue = dailyNewKanjiField)
                    }

                    val initialEaseField: String = state.value.initialEaseField
                    if (initialEaseField.isNotBlank() && initialEaseField.count { it != '.' && !it.isDigit() } == 0 && initialEaseField.count { it.isDigit() } >= 1 && initialEaseField.count { it == '.' } <= 1) {
                        kanjiRepository.updateSettings(code = "initial_ease", setValue = "%.2f".format(initialEaseField.toFloat()))
                    }

                    _state.update {
                        it.copy(
                            dailyNewKanji = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue,
                            initialEase = kanjiRepository.getSettingsFromCode(code = "initial_ease").setValue
                        )
                    }
                }
            }

            SettingsEvent.ApplyDefaultSettings -> {
                viewModelScope.launch {
                    val dailyNewKanjiDefault: String = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").defaultValue
                    val initialEaseDefault: String = kanjiRepository.getSettingsFromCode(code = "initial_ease").defaultValue

                    kanjiRepository.updateSettings(code = "daily_new_kanji", setValue = dailyNewKanjiDefault)
                    kanjiRepository.updateSettings(code = "initial_ease", setValue = initialEaseDefault)
                    _state.update {
                        it.copy(
                            dailyNewKanji = dailyNewKanjiDefault,
                            initialEase = initialEaseDefault
                        )
                    }
                }
            }
        }
    }
}