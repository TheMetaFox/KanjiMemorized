package com.example.kanjimemorized.ui.screens.study.flashcard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.ui.screens.study.review.ReviewEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.PriorityQueue
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
            is FlashcardEvent.SetStudyType -> {
                _state.update {
                    it.copy(
                        studyType = flashcardEvent.studyType
                    )
                }
            }
            is FlashcardEvent.InitializeQueue -> {
                when(state.value.studyType) {
                    StudyType.New -> {
                        viewModelScope.launch(
                            block = {
                                val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
                                kanjiRepository.getUnlockedKanjiList().forEach { kanji ->
                                    if (kanji.durability > 0f) {
                                        //Log.i("FlashcardViewModel.kt", "${kanji.unicode} has durability greater than 0.")
                                        return@forEach
                                    }
                                    queue.add(Pair(kanji.strokes.toFloat(), kanji))
                                    Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} to learn queue with priority ${kanji.strokes}...")
                                }
                                Log.i("FlashcardViewModel.kt", "Queue size: ${queue.size}")
                                if(queue.peek() != null) {
                                    val i : Kanji = queue.poll()!!.second
                                    Log.i("FlashcardViewModel.kt", "Polling kanji ${i.unicode}...")
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
                                } else {
                                    Log.i("FlashcardViewModel.kt", "Empty queue...")
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
                    StudyType.Review -> {
                        viewModelScope.launch(
                            block = {
                                val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
                                kanjiRepository.getUnlockedKanjiList().forEach { kanji ->
                                    if (kanji.durability == 0f) {
                                        //Log.i("FlashcardViewModel.kt", "${kanji.unicode} has no durability.")
                                        return@forEach
                                    }
                                    else if (kanjiRepository.getRetentionFromKanji(kanji.unicode) > .80f) {
                                        //Log.i("FlashcardViewModel.kt", "${kanji.unicode} has retention greater than 80%.")
                                        return@forEach
                                    }
                                    queue.add(Pair(kanjiRepository.getRetentionFromKanji(kanji.unicode), kanji))
                                    Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} to review queue with priority ${kanjiRepository.getRetentionFromKanji(kanji.unicode)}...")
                                }
                                Log.i("FlashcardViewModel.kt", "Queue size: ${queue.size}")
                                if(queue.peek() != null) {
                                    val i : Kanji = queue.poll()!!.second
                                    Log.i("FlashcardViewModel.kt", "Polling kanji ${i.unicode}")
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
                                } else {
                                    Log.i("FlashcardViewModel.kt", "Empty queue...")
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
                    StudyType.Mixed -> {
                        viewModelScope.launch(
                            block = {
                                val queue : PriorityQueue<Pair<Float, Kanji>> = PriorityQueue(compareBy { it.first })
                                var newCount = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue.toInt() - kanjiRepository.getEarliestDateCountFromToday()
                                kanjiRepository.getUnlockedKanjiList().forEach { kanji ->
                                    if (kanjiRepository.getRetentionFromKanji(kanji.unicode) > .80f) {
                                        //Log.i("FlashcardViewModel.kt", "${kanji.unicode} has retention greater than 80%.")
                                        return@forEach
                                    }
                                    else if (kanji.durability == 0f) {
                                        if (newCount == 0) {
                                            return@forEach
                                        }
                                        Log.i("FlashcardViewModel.kt", "${kanji.unicode} with Durability: ${kanji.durability}")
                                        Log.i("FlashcardViewModel.kt", "New Kanji #$newCount: ${kanji.unicode}")
                                        newCount--
                                    }
                                    queue.add(Pair(kanjiRepository.getRetentionFromKanji(kanji.unicode), kanji))
                                    Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} to review queue with priority ${kanjiRepository.getRetentionFromKanji(kanji.unicode)}...")
                                }

                                Log.i("FlashcardViewModel.kt", "Queue size: ${queue.size}")
                                if(queue.peek() != null) {
                                    val i : Kanji = queue.poll()!!.second
                                    Log.i("FlashcardViewModel.kt", "Polling kanji ${i.unicode}")
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
                                } else {
                                    Log.i("FlashcardViewModel.kt", "Empty queue...")
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
                }
            }
            is FlashcardEvent.FlipFlashcard -> {
                _state.update(
                    function = {
                        it.copy(
                            isAnswerShowing = !state.value.isAnswerShowing
                        )
                    }
                )
            }
            is FlashcardEvent.GetRandomFlashcard -> {
                viewModelScope.launch(
                    block = {
                        val i : Kanji? = state.value.queue.poll()?.second
                        if (i != null) {
                            Log.i("FlashcardViewModel.kt", "Polling kanji ${i.unicode}...")
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
                            FlashcardEvent.InitializeQueue
                        }
                    }
                )
            }
            is FlashcardEvent.WrongCard -> {
                viewModelScope.launch {
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

                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.insertReview(
                        review = review
                    )
                    state.value.queue.add(Pair(kanjiRepository.getRetentionFromKanji(kanji.unicode), kanji))
                    Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} back into queue...")
                }
            }
            is FlashcardEvent.CorrectCard -> {
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
            is FlashcardEvent.EasyCard -> {
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