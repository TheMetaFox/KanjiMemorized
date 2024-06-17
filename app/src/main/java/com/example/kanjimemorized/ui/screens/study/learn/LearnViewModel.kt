package com.example.kanjimemorized.ui.screens.study.learn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.PriorityQueue

class LearnViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _state: MutableStateFlow<LearnState> = MutableStateFlow(
        value = LearnState()
    )

//    private val _queue: StateFlow<PriorityQueue<Pair<Float, Kanji>>> = MutableStateFlow(
//        value = kanjiRepository.getKanjiList()
//    )


    val state: StateFlow<LearnState> = _state
//    val state: StateFlow<LearnState> = combine(
//            flow = _state,
//            flow2 = _queue,
//            transform = { state, queue ->
//                state.copy(
//                    queue = queue
//                )
//            }
//        )
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted
//                .WhileSubscribed(5000),
//            initialValue = LearnState()
//        )


    fun onEvent(
        learnEvent: LearnEvent
    ) {
        when(learnEvent) {
            is LearnEvent.InitializeQueue -> {
                viewModelScope.launch(
                    block = {
                    val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
                    kanjiRepository.getKanjiList().forEach { kanji ->
                        if (kanji.durability > 0f) {
                            Log.i("LearnViewModel.kt", "${kanji.unicode} has durability greater than 0...")
                        }
                        else {
                            queue.add(Pair(kanji.strokes.toFloat(), kanji))
                            Log.i("LearnViewModel.kt", "Adding ${kanji.unicode} to learn queue with priority ${kanji.strokes}...")
                        }
                    }
                        Log.i("LearnViewModel.kt", "Queue size: ${queue.size}")
                        if(queue.peek() != null) {
                            val i : Kanji = queue.poll()!!.second
                            Log.i("LearnViewModel.kt", "Polling kanji ${i.unicode}")
                            Log.i("LearnViewModel.kt", "State updating...")
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
                            Log.i("LearnViewModel.kt", "State updated.")
                        }
                    }
                )
            }
            is LearnEvent.FlipFlashcard -> {
                val isAnswerShowing: Boolean = state.value.isAnswerShowing
                _state.update(
                    function = {
                        it.copy(
                            isAnswerShowing = !isAnswerShowing
                        )
                    }
                )
            }
            is LearnEvent.GetRandomFlashcard -> {
                viewModelScope.launch(
                    block = {
                        val i : Kanji? = state.value.queue.poll()?.second
                        if (i != null) {
                            _state.update(
                                function = {
                                    it.copy(
                                        kanji = i,
                                        meanings = kanjiRepository.getMeaningsFromKanji(i.unicode),
                                        isAnswerShowing = state.value.isAnswerShowing,
                                        isReviewAvailable = state.value.isReviewAvailable,
                                    )
                                }
                            )
                        }
                    }
                )
            }
            is LearnEvent.WrongCard -> {
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
            is LearnEvent.CorrectCard -> {
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
            is LearnEvent.EasyCard -> {
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