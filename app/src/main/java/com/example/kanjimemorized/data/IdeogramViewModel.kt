package com.example.kanjimemorized.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeogramViewModel(private val dao: IdeogramDao): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.UNICODE)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _ideograms = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.UNICODE -> dao.getIdeogramOrderedByUnicode()
                SortType.STROKES -> dao.getIdeogramOrderedByStrokes()
                SortType.RETENTION -> dao.getIdeogramOrderedByRetention()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(IdeogramState())
    val state = combine(_state,_sortType,_ideograms) { state, sortType, ideograms ->
        state.copy(
            ideograms = ideograms,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IdeogramState())

    fun onEvent(event: IdeogramEvent) {
        when(event) {
            is IdeogramEvent.DeleteIdeogram -> {
                viewModelScope.launch {
                    dao.deleteIdeogram(event.ideogram)
                }
            }
            IdeogramEvent.HideDialog -> {
                _state.update {
                    it.copy(
                    isAddingIdeogram = false
                    )
                }
            }
            IdeogramEvent.SaveIdeogram -> {
                val unicode = state.value.unicode
                val meanings = state.value.meanings
                val strokes = state.value.strokes

                if(unicode.isBlank() || meanings.isBlank() || strokes.isBlank()) {
                    return
                }

                val ideogram = Ideogram(
                    unicode = unicode,
                    meanings = meanings,
                    strokes = Integer.parseInt(strokes),
                    decompositions = "",
                    retention = 0f,
                    coercivity = 0f
                )
                viewModelScope.launch {
                    dao.insertIdeogram(ideogram)
                }
                _state.update {
                    it.copy(
                    isAddingIdeogram = false,
                    unicode = "",
                    meanings = "",
                    strokes = ""
                    )
                }
            }
            is IdeogramEvent.SetUnicode -> {
                _state.update {
                    it.copy(
                    unicode = event.unicode.toString()
                    )
                }
            }
            is IdeogramEvent.SetMeanings -> {
                _state.update {
                    it.copy(
                    meanings = event.meanings.toString()
                    )
                }
            }
            is IdeogramEvent.SetStrokes -> {
                _state.update {
                    it.copy(
                    strokes = event.strokes.toString()
                    )
                }
            }
            IdeogramEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                    isAddingIdeogram = true
                    )
                }
            }
            is IdeogramEvent.SortIdeograms -> {
                _sortType.value = event.sortType
            }
        }
    }
}