package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FlashcardViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

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
                                var i = kanjiRepository.getRandomKanji()
                                while (state.value.kanji == i) {
                                    i = kanjiRepository.getRandomKanji()
                                }
                                it.copy(
                                    kanji = i,
                                    isAnswerShowing = false
                                )
                            }
                        )
                    }
                )
            }
            is FlashcardEvent.WrongCard -> {
                var durability: Float = state.value.kanji!!.durability
                if (durability < 1) {
                    durability = 0f
                } else {
                    durability -= 1
                }

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
                    meanings = state.value.kanji!!.meanings,
                    strokes = state.value.kanji!!.strokes,
                    durability = durability,
                )

                val review = Review(
                    date = LocalDateTime.now(),
                    unicode = state.value.kanji!!.unicode,
                    rating = 1
                )

                viewModelScope.launch {
                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.upsertReview(
                        review = review
                    )
                }
            }
            is FlashcardEvent.CorrectCard -> {
                var durability: Float = state.value.kanji!!.durability
                durability += 1

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
                    meanings = state.value.kanji!!.meanings,
                    strokes = state.value.kanji!!.strokes,
                    durability = durability,
                )

                val review = Review(
                    date = LocalDateTime.now(),
                    unicode = state.value.kanji!!.unicode,
                    rating = 2
                )

                viewModelScope.launch {
                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.upsertReview(
                        review = review
                    )
                }
            }
            is FlashcardEvent.EasyCard -> {
                var durability: Float = state.value.kanji!!.durability
                durability += 2

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
                    meanings = state.value.kanji!!.meanings,
                    strokes = state.value.kanji!!.strokes,
                    durability = durability,
                )

                val review = Review(
                    date = LocalDateTime.now(),
                    unicode = state.value.kanji!!.unicode,
                    rating = 3
                )

                viewModelScope.launch {
                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.upsertReview(
                        review = review
                    )
                }
            }
        }
    }
}