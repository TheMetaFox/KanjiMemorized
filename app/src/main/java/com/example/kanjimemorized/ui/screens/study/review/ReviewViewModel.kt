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
import java.util.PriorityQueue
import java.util.Queue
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
            is ReviewEvent.InitializeQueue -> {
                viewModelScope.launch(
                    block = {
                        val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
                        kanjiRepository.getKanjiList().forEach { kanji ->
                            if (kanji.durability == 0f) {
                                Log.i("ReviewViewModel.kt", "${kanji.unicode} has no durability.")
                            }
                            else if (kanjiRepository.getRetentionFromKanji(kanji.unicode) > .80f) {
                                Log.i("ReviewViewModel.kt", "${kanji.unicode} has retention greater than 80%.")
                            }
                            else {
                                queue.add(Pair(1/kanjiRepository.getRetentionFromKanji(kanji.unicode), kanji))
                                Log.i("ReviewViewModel.kt", "Adding ${kanji.unicode} to review queue with priority ${1/kanjiRepository.getRetentionFromKanji(kanji.unicode)}...")
                            }
                        }
                        Log.i("ReviewViewModel.kt", "Queue size: ${queue.size}")
                        if(queue.peek() != null) {
                            val i : Kanji = queue.poll()!!.second
                            Log.i("ReviewViewModel.kt", "Polling kanji ${i.unicode}")
                            Log.i("ReviewViewModel.kt", "State updating...")
                            _state.update(
                                function = {
                                    it.copy(
                                        kanji = i,
                                        meanings = kanjiRepository.getMeaningsFromKanji(i.unicode),
                                        isReviewAvailable = true,
                                        queue = queue
                                    )
                                }
                            )
                            Log.i("ReviewViewModel.kt", "State updated.")
                        }
                    }
                )

            }
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
                        val i : Kanji? = state.value.queue.poll()?.second
                        if (i != null) {
                            _state.update(
                                function = {
                                    it.copy(
                                        kanji = i,
                                        meanings = kanjiRepository.getMeaningsFromKanji(i.unicode),
                                        isReviewAvailable = true
                                    )
                                }
                            )
                        }
                        else {
                            _state.update(
                                function = {
                                    it.copy(
                                        isReviewAvailable = false
                                    )
                                }
                            )
                        }
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
                }
            }
        }
    }
}