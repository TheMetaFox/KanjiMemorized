package com.example.kanjimemorized.ui.screens.library.kanji

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.ui.screens.settings.SettingType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KanjiViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _state: MutableStateFlow<KanjiState> = MutableStateFlow(
        value = KanjiState()
    )

    val state: StateFlow<KanjiState> = _state

    fun onEvent(
        kanjiEvent: KanjiEvent
    ) {
        when(kanjiEvent) {
            is KanjiEvent.DisplayKanjiInfo -> {
                _state.update(
                    function = {
                        it.copy(
                            kanji = kanjiEvent.kanji
                        )
                    }
                )
                viewModelScope.launch {
                    _state.update(
                        function = {
                            it.copy(
                                meaning = kanjiRepository.getMeaningsFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                ),
                                retention = kanjiRepository.getRetentionFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                ),
                                components = kanjiRepository.getKanjiComponentsFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                ),
                                componentMeaning = kanjiRepository.getKanjiComponentMeaningsFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                ),
                                componentsLatestDates = kanjiRepository.getKanjiComponentsLatestDatesFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                ),
                                reviews = kanjiRepository.getReviewsFromKanji(
                                    kanji = kanjiEvent.kanji.unicode
                                )
                            )
                        }
                    )
                }
            }
            is KanjiEvent.ShowKanjiReviewData -> {
                _state.update(
                    function = {
                        it.copy(
                            isShowingReviewData = true
                        )
                    }
                )
            }
            is KanjiEvent.HideKanjiReviewData -> {
                _state.update(
                    function = {
                        it.copy(
                            isShowingReviewData = false
                        )
                    }
                )
            }
            is KanjiEvent.RestartKanjiProgress -> {
                viewModelScope.launch {
                    kanjiRepository.upsertKanji(
                        kanji = Kanji(
                            unicode = state.value.kanji.unicode,
                            strokes = state.value.kanji.strokes,
                            durability = 0f,
                            ease = kanjiRepository.getSettingsFromCode(settingType = SettingType.INITIAL_EASE).setValue.toFloat()
                        )
                    )
                    kanjiRepository.deleteReviewsFromKanji(kanji = state.value.kanji.unicode)
                    _state.update(
                        function = {
                            it.copy(
                                kanji = Kanji(
                                    unicode = state.value.kanji.unicode,
                                    strokes = state.value.kanji.strokes,
                                    durability = 0f,
                                    ease = kanjiRepository.getSettingsFromCode(settingType = SettingType.INITIAL_EASE).setValue.toFloat()
                                ),
                                retention = 0f,
                                reviews = listOf()
                            )
                        }
                    )
                }
            }
        }
    }
}