package com.example.kanjimemorized.ui.screens.study.review

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

class ReviewViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _state: MutableStateFlow<ReviewState> = MutableStateFlow(
        value = ReviewState()
    )

    val state: StateFlow<ReviewState> = _state

    fun onEvent(
        reviewEvent: ReviewEvent
    ) {
        when(reviewEvent) {
            is ReviewEvent.FlipFlashcard -> {
                val isAnswerShowing: Boolean = state.value.isAnswerShowing
                _state.update(
                    function = {
                        it.copy(
                            isAnswerShowing = !isAnswerShowing
                        )
                    }
                )
            }
            is ReviewEvent.GetRandomFlashcard -> {
                viewModelScope.launch(
                    block = {
                        _state.update(
                            function = {
                                var i : Kanji
                                var ready : Boolean
                                do {
                                    i = kanjiRepository.getRandomKanji()
                                    ready = true
                                    Log.i("ReviewViewModel.kt", "Checking if $i is ready for review...")
                                    if (i.durability == 0f) {
                                        Log.i("ReviewViewModel.kt", "${i.unicode} has no durability.")
                                        ready = false
                                    }
                                    val latestDate = kanjiRepository.getLatestDateFromKanji(i.unicode)
                                    val retention = if (latestDate == null) 0f else exp(-(((Duration.between(
                                        latestDate,
                                        LocalDateTime.now()
                                    ).toMinutes()).toDouble()/1440) / i.durability)).toFloat()
                                    if (retention > .80f) {
                                        Log.i("ReviewViewModel.kt", "${i.unicode} has retention greater than 80%.")
                                        ready = false
                                    }
                                } while (state.value.kanji == i || !ready)
                                it.copy(
                                    kanji = i,
                                    meanings = kanjiRepository.getMeaningsFromKanji(i.unicode),
                                    isAnswerShowing = false,
                                    isReviewAvailable = true,
                                )
                            }
                        )
                    }
                )
            }
            is ReviewEvent.WrongCard -> {
                var durability: Float = state.value.kanji!!.durability
                if (durability <= 1) {
                    durability = 0f
                } else {
                    durability -= 1
                }

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
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
                    kanjiRepository.insertReview(
                        review = review
                    )
                    _state.update(
                        function = {
                            it.copy(
                                isReviewAvailable = false
                            )
                        }
                    )
                }
            }
            is ReviewEvent.CorrectCard -> {
                var durability: Float = state.value.kanji!!.durability
                durability += 1

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
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
                    kanjiRepository.insertReview(
                        review = review
                    )
                    _state.update(
                        function = {
                            it.copy(
                                isReviewAvailable = false
                            )
                        }
                    )

                }
            }
            is ReviewEvent.EasyCard -> {
                var durability: Float = state.value.kanji!!.durability
                durability += 2

                val kanji = Kanji(
                    unicode = state.value.kanji!!.unicode,
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
                    kanjiRepository.insertReview(
                        review = review
                    )
                    _state.update(
                        function = {
                            it.copy(
                                isReviewAvailable = false
                            )
                        }
                    )

                }
            }
        }
    }
}