package com.example.kanjimemorized.ui.screens.study.flashcard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.exp

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
                                var i : Kanji
                                var available : Boolean
                                //Log.i("KanjiMemorized", available.toString())
                                do {
                                    i = kanjiRepository.getRandomKanji()
                                    available = true
                                    Log.i("FlashcardViewModel.kt", "Checking if ${i} is available...")
                                    kanjiRepository.getKanjiComponentsFromKanji(i.unicode).forEach { kanji ->
                                        Log.i("FlashcardViewModel.kt", "Checking composition ${kanji.unicode}-${kanji.meanings}...")
                                        if (kanji.durability == 0f) {
                                            Log.i("FlashcardViewModel.kt", "${kanji.unicode} has no durability.")
                                            available = false
                                        }
                                        val latestDate = kanjiRepository.getLatestDateFromKanji(kanji.unicode)
                                        val retention = if (latestDate == null) 0f else exp(-(((Duration.between(
                                            kanjiRepository.getLatestDateFromKanji(kanji.unicode),
                                            LocalDateTime.now()
                                        ).toMinutes()).toDouble()/1440) / kanji.durability)).toFloat()
                                        if (retention < .80f) {
                                            Log.i("FlashcardViewModel.kt", "${kanji.unicode} has retention less than 80%.")
                                            available = false
                                        }
                                    }
                                } while (state.value.kanji == i || !available)
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
                if (durability <= 1) {
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
                    date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
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
                    date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
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
                    date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
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