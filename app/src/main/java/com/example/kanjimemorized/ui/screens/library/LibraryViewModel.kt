package com.example.kanjimemorized.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {
    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(
        value = SortType.UNICODE
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _ideograms: StateFlow<List<Ideogram>> = _sortType
        .flatMapLatest(
            transform = { sortType ->
                when(sortType) {
                    SortType.UNICODE -> ideogramRepository
                        .getIdeogramListOrderedByUnicode()
                    SortType.STROKES -> ideogramRepository
                        .getIdeogramListOrderedByStrokes()
                    SortType.RETENTION -> ideogramRepository
                        .getIdeogramListOrderedByRetention()
                }
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted
                .WhileSubscribed(),
            initialValue = emptyList()
        )
    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(
        value = LibraryState()
    )
    val state: StateFlow<LibraryState> = combine(
        flow = _state,
        flow2 = _sortType,
        flow3 = _ideograms,
        transform = { state, sortType, ideograms ->
            state.copy(
                ideograms = ideograms,
                sortType = sortType
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
            is  LibraryEvent.SortIdeograms -> {
                _sortType.value = libraryEvent.sortType
            }
        }
    }
}