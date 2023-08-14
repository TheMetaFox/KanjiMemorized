package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.IdeogramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeogramViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {

    private val _state: MutableStateFlow<IdeogramState> = MutableStateFlow(
        value = IdeogramState()
    )

    val state: StateFlow<IdeogramState> = _state

    fun onEvent(
        ideogramEvent: IdeogramEvent
    ) {
        when(ideogramEvent) {
            is IdeogramEvent.DisplayIdeogramInfo -> {
                _state.update(
                    function = {
                        it.copy(
                            ideogram = ideogramEvent.ideogram
                        )
                    }
                )
                viewModelScope.launch {
                    if (ideogramEvent.ideogram.decompositions.isNullOrEmpty()) {
                        _state.update(
                            function = {
                                it.copy(
                                    decompositions = null
                                )
                            }
                        )
                    } else {
                        _state.update(
                            function = {
                                it.copy(
                                    decompositions = ideogramRepository.getIdeogramDecompositionsList(
                                        decompositions = ideogramEvent.ideogram.decompositions
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}