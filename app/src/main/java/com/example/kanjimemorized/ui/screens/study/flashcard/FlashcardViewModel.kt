package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramRepository
import com.example.kanjimemorized.ui.screens.ideogram.IdeogramEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {

    private val _state: MutableStateFlow<FlashcardState> = MutableStateFlow(
        value = FlashcardState()
    )

    val state: StateFlow<FlashcardState> = _state

    fun onEvent(
        flashcardEvent: FlashcardEvent
    ) {
        when(flashcardEvent) {
            is FlashcardEvent.FlipFlashcard -> {
                val isAnswerShowing: Boolean = state.value.isAnswerShowing
                _state.update(
                    function = {
                        it.copy(
                            isAnswerShowing = !isAnswerShowing
                        )
                    }
                )
            }
            is FlashcardEvent.GetRandomFlashcard -> {
                viewModelScope.launch(
                    block = {
                        _state.update(
                            function = {
                                it.copy(
                                    ideogram = ideogramRepository.getRandomIdeogram()
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}