package com.example.kanjimemorized.ui.screens.ideogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramEvent
import com.example.kanjimemorized.data.IdeogramRepository
import com.example.kanjimemorized.data.IdeogramState
import com.example.kanjimemorized.data.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeogramViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {
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
    private val _state: MutableStateFlow<IdeogramState> = MutableStateFlow(
        value = IdeogramState()
    )
    val state: StateFlow<IdeogramState> = combine(
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
            initialValue = IdeogramState()
        )

    fun onEvent(
        event: IdeogramEvent
    ) {
        when(event) {
            is IdeogramEvent.DeleteIdeogram -> {
                viewModelScope.launch {
                    ideogramRepository.deleteIdeogram(
                        ideogram = event.ideogram
                    )
                }
            }
            IdeogramEvent.HideDialog -> {
                _state.update(
                    function = {
                        it.copy(
                            isAddingIdeogram = false
                        )
                    }
                )
            }
            IdeogramEvent.SaveIdeogram -> {
                val unicode = state.value.unicode
                val meanings = state.value.meanings
                val strokes = state.value.strokes

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
                    strokes = strokes,
                    decompositions = "",
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
            is IdeogramEvent.SetUnicode -> {
                _state.update(
                    function = {
                        it.copy(
                            unicode = event.unicode
                        )
                    }
                )
            }
            is IdeogramEvent.SetMeanings -> {
                _state.update(
                    function = {
                        it.copy(
                            meanings = event.meanings
                        )
                    }
                )
            }
            is IdeogramEvent.SetStrokes -> {
                _state.update(
                    function = {
                        it.copy(
                            strokes = event.strokes
                        )
                    }
                )
            }
            IdeogramEvent.ShowDialog -> {
                _state.update(
                    function = {
                        it.copy(
                            isAddingIdeogram = true
                        )
                    }
                )
            }
            is IdeogramEvent.SortIdeograms -> {
                _sortType.value = event.sortType
            }
        }
    }
}