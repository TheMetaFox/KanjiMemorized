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
            is LibraryEvent.DeleteIdeogram -> {
                viewModelScope.launch(
                    block = {
                        ideogramRepository.deleteIdeogram(
                            ideogram = libraryEvent.ideogram
                        )
                    }
                )
            }
            LibraryEvent.HideDialog -> {
                _state.update(
                    function = {
                        it.copy(
                            isAddingIdeogram = false,
                            unicode = "",
                            meanings = "",
                            strokes = ""
                        )
                    }
                )
            }
            LibraryEvent.SaveIdeogram -> {
                val unicode: String = state.value.unicode
                val meanings: String = state.value.meanings
                val strokes: String = state.value.strokes

                if (unicode.isBlank() || meanings.isBlank() || strokes.isBlank()) {
                    return
                }
                for (char in 71..90) {
                    if (unicode.contains(char.toChar(), true)) {
                        return
                    }
                }

                val ideogram = Ideogram(
                    unicode = Integer.parseInt(unicode, 16).toChar(),
                    meanings = meanings.split(","),
                    strokes = Integer.parseInt(strokes),
                    decompositions = null,
                    retention = 0f,
                    coercivity = 0f
                )
                viewModelScope.launch(
                    block = {
                        ideogramRepository.insertIdeogram(
                            ideogram = ideogram
                        )
                    }
                )
                _state.update(
                    function = {
                        it.copy(
                            isAddingIdeogram = false,
                            unicode = "",
                            meanings = "",
                            strokes = ""
                        )
                    }
                )
            }
            is LibraryEvent.SetUnicode -> {
                _state.update(
                    function = {
                        it.copy(
                            unicode = libraryEvent.unicode
                        )
                    }
                )
            }
            is LibraryEvent.SetMeanings -> {
                _state.update(
                    function = {
                        it.copy(
                            meanings = libraryEvent.meanings
                        )
                    }
                )
            }
            is LibraryEvent.SetStrokes -> {
                _state.update(
                    function = {
                        it.copy(
                            strokes = libraryEvent.strokes
                        )
                    }
                )
            }
            LibraryEvent.ShowDialog -> {
                _state.update(
                    function = {
                        it.copy(
                            isAddingIdeogram = true
                        )
                    }
                )
            }
            is LibraryEvent.SortIdeograms -> {
                _sortType.value = libraryEvent.sortType
            }
        }
    }
}