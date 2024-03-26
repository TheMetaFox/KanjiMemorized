package com.example.kanjimemorized.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class LibraryViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {
    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(
        value = SortType.UNICODE
    )
    private val _filterNonStudyable: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _kanji: StateFlow<List<Kanji>> = _sortType
        .flatMapLatest(
            transform = { sortType ->
                when(sortType) {
                    SortType.UNICODE -> kanjiRepository
                        .getKanjiOrderedByUnicode()
                    SortType.STROKES -> kanjiRepository
                        .getKanjiOrderedByStrokes()
                    SortType.DURABILITY -> kanjiRepository
                        .getKanjiOrderedByDurability()
                }
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(),
            initialValue = emptyList()
        )
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _date: StateFlow<List<LocalDateTime>> = _sortType
        .flatMapLatest(
            transform = { sortType ->
                when(sortType) {
                    SortType.UNICODE -> kanjiRepository
                        .getLatestDateOrderedByUnicode()
                    SortType.STROKES -> kanjiRepository
                        .getLatestDateOrderedByUnicode()
                    SortType.DURABILITY -> kanjiRepository
                        .getLatestDateOrderedByUnicode()
                }
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(),
            initialValue = emptyList()
        )
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _retention: StateFlow<List<Float>> =  _sortType
        .flatMapLatest(
            transform = { sortType ->
                when(sortType) {
                    SortType.UNICODE -> kanjiRepository
                        .getRetentionOrderedByUnicode()
                    SortType.STROKES -> kanjiRepository
                        .getRetentionOrderedByUnicode()
                    SortType.DURABILITY -> kanjiRepository
                        .getRetentionOrderedByUnicode()
                }
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(),
            initialValue = emptyList()
        )
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _kanjiRetention: StateFlow<List<Pair<Kanji, Float>>> = _sortType
        .flatMapLatest(
            transform = { sortType ->
                when(sortType) {
                    SortType.UNICODE -> kanjiRepository
                        .getKanjiRetentionOrderedByUnicode()
                    SortType.STROKES -> kanjiRepository
                        .getKanjiRetentionOrderedByUnicode()
                    SortType.DURABILITY -> kanjiRepository
                        .getKanjiRetentionOrderedByUnicode()
                }
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(),
            initialValue = emptyList()
        )
    private val _kanjiRetention1: Flow<List<Pair<Kanji, Float>>> = combine(
        flow = _kanji,
        flow2 = _retention,
        transform = { kanji, retention ->
            kanji.zip(retention)
        }
    )
    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(
        value = LibraryState()
    )
    val state: StateFlow<LibraryState> = combine(
        flow = _state,
        flow2 = _sortType,
        flow3 = _kanji,
        flow4 = _date,
        flow5 = _retention,
        transform = { state, sortType, kanji, date, retention ->
            state.copy(
                kanji = kanji,
                date = date,
                sortType = sortType,
                retention = retention,
            )
        }
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(5000),
            initialValue = LibraryState()
        )

    fun onEvent(
        libraryEvent: LibraryEvent
    ) {
        when(libraryEvent) {
            is LibraryEvent.SortKanji -> {
                _sortType.value = libraryEvent.sortType
            }
            is LibraryEvent.ToggleFilterNonStudyable -> {
                _filterNonStudyable.value != libraryEvent.filterNonStudyable
            }
            is LibraryEvent.ResetKanji -> {
                viewModelScope.launch {
                    kanjiRepository.initializeKanjiData()
                }
            }
            is LibraryEvent.GetRetentionFromKanji -> {
                viewModelScope.launch {
                    _state.update(
                        function = {
                            it.copy(
                                retention1 = kanjiRepository.getRetentionFromKanji(
                                    kanji = libraryEvent.kanji)
                            )
                        }
                    )
                }
            }
        }
    }
}