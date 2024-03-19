package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

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
                                var i = ideogramRepository.getRandomStudyableIdeogram()
                                while (state.value.ideogram == i) {
                                    i = ideogramRepository.getRandomStudyableIdeogram()
                                }
                                it.copy(
                                    ideogram = i,
                                    isAnswerShowing = false
                                )
                            }
                        )
                    }
                )
            }
            is FlashcardEvent.WrongCard -> {
                var retention: Float = state.value.ideogram!!.retention
                var coercivity: Float = state.value.ideogram!!.coercivity
                retention += retention/2
                coercivity -= 1
                val phase: LocalDate = LocalDate.now()
                val period = Math.floor(retention*coercivity.toDouble()).toInt()

                val ideogram: Ideogram = Ideogram(
                    unicode = state.value.ideogram!!.unicode,
                    meanings = state.value.ideogram!!.meanings,
                    strokes = state.value.ideogram!!.strokes,
                    decompositions = state.value.ideogram!!.decompositions,
                    retention = retention,
                    coercivity = coercivity,
                    phase = phase,
                    period = period,
                )
                viewModelScope.launch {
                    ideogramRepository.updateIdeogram(
                        ideogram = ideogram
                    )
                }
                _state.update(
                    function = {
                        it.copy(
                            ideogram = ideogram
                        )
                    }
                )
            }
            is FlashcardEvent.CorrectCard -> {
                var retention: Float = state.value.ideogram!!.retention
                var coercivity: Float = state.value.ideogram!!.coercivity
                retention += (1 - retention)/2
                coercivity += 1
                val phase: LocalDate = LocalDate.now()
                val period = Math.floor(retention*coercivity.toDouble()).toInt()

                val ideogram: Ideogram = Ideogram(
                    unicode = state.value.ideogram!!.unicode,
                    meanings = state.value.ideogram!!.meanings,
                    strokes = state.value.ideogram!!.strokes,
                    decompositions = state.value.ideogram!!.decompositions,
                    retention = retention,
                    coercivity = coercivity,
                    phase = phase,
                    period = period,
                )
                viewModelScope.launch {
                    ideogramRepository.updateIdeogram(
                        ideogram = ideogram
                    )
                }
                _state.update(
                    function = {
                        it.copy(
                            ideogram = ideogram
                        )
                    }
                )
            }
            is FlashcardEvent.EasyCard -> {
                var retention: Float = state.value.ideogram!!.retention
                var coercivity: Float = state.value.ideogram!!.coercivity
                retention += (1 - retention)*4/5
                coercivity += 2
                val phase: LocalDate = LocalDate.now()
                val period = Math.floor(retention*coercivity.toDouble()).toInt()

                val ideogram: Ideogram = Ideogram(
                    unicode = state.value.ideogram!!.unicode,
                    meanings = state.value.ideogram!!.meanings,
                    strokes = state.value.ideogram!!.strokes,
                    decompositions = state.value.ideogram!!.decompositions,
                    retention = retention,
                    coercivity = coercivity,
                    phase = phase,
                    period = period,
                )
                viewModelScope.launch {
                    ideogramRepository.updateIdeogram(
                        ideogram = ideogram
                    )
                }
                _state.update(
                    function = {
                        it.copy(
                            ideogram = ideogram
                        )
                    }
                )
            }
        }
    }
}