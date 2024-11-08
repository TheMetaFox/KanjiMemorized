package com.example.kanjimemorized.ui.screens.study.flashcard

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
                Log.i("FlashcardViewModel.kt", state.value.studyType.name)
                when(state.value.studyType) {
                    StudyType.New -> {
                        Log.i("FlashcardViewModel.kt", "Initializing queue with study type New...")
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
                                val unlockedKanjiList: List<Kanji> = kanjiRepository.getUnlockedKanjiList()
                                val knownKanjiList: List<Kanji> = kanjiRepository.getKnownKanjiList()
                                var newCount = kanjiRepository.getSettingsFromCode(code = "daily_new_kanji").setValue.toInt() - kanjiRepository.getEarliestDateCountFromToday()

                                unlockedKanjiList.forEach { kanji ->
                                    if (kanji.durability == 0f) {
                                        if (newCount == 0) {
                                            return@forEach
                                        }
//                                        Log.i("FlashcardViewModel.kt", "${kanji.unicode} with Durability: ${kanji.durability}")
                                        Log.i("FlashcardViewModel.kt", "New Kanji #$newCount: ${kanji.unicode}")
                                        newCount--

                                        queue.add(Pair(10f, kanji))
                                        Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} with priority ${10f}...")
                                    }
                                }
                                knownKanjiList.forEach { kanji ->
                                    if (kanjiRepository.getRetentionFromKanji(kanji.unicode) > .80f) {
//                                        Log.i("FlashcardViewModel.kt", "${kanji.unicode} has retention greater than 80%.")
                                        return@forEach
                                    } else {
                                        if (unlockedKanjiList.contains(kanji)) {
                                            queue.add(Pair(kanjiRepository.getRetentionFromKanji(kanji.unicode), kanji))
                                            Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} with priority ${kanjiRepository.getRetentionFromKanji(kanji.unicode)}...")
                                        } else {
                                            queue.add(Pair(Float.POSITIVE_INFINITY, kanji))
                                            Log.i("FlashcardViewModel.kt", "Adding ${kanji.unicode} with priority ${Float.POSITIVE_INFINITY}...")
                                        }
                                    }
                                }
                                Log.i("FlashcardViewModel.kt", "Queue size: ${queue.size}")
                                if (queue.isEmpty()) {
                                    Log.i("FlashcardViewModel.kt", "Empty queue...")
                                    _state.update(
                                        function = {
                                            it.copy(
                                                isReviewAvailable = false
                                            )
                                        }
                                    )
                                } else {
                                    Log.i("FlashcardViewModel.kt", "Queue Initialized: \n${queue.joinToString("\n")}")
                                    _state.update(
                                        function = {
                                            it.copy(
                                                isReviewAvailable = true,
                                                queue = queue
                                            )
                                        }
                                    )
                                }
                                onEvent(FlashcardEvent.GetRandomFlashcard)
                            }
                        )
                    }
                }
            }
            is FlashcardEvent.RefreshQueue -> {
                viewModelScope.launch(
                    block = {
                        val queue: PriorityQueue<Pair<Float, Kanji>> = state.value.queue
                        val unlockedKanjiList: List<Kanji> = kanjiRepository.getUnlockedKanjiList()

                        PriorityQueue(queue).forEach { card ->
                            if (card.first != Float.POSITIVE_INFINITY) return@forEach
                            if (card.second.durability == 0f) {
                                if (queue.peek()!!.first == Float.POSITIVE_INFINITY) {
                                    Log.i("FlashcardViewModel.kt", "Changing ${card.second.unicode} priority ${Float.POSITIVE_INFINITY} to ${card.second.durability}...")
                                    queue.add(Pair(card.second.durability, card.second))
                                    queue.remove(card)
                                }
                            } else {
                                if (unlockedKanjiList.contains(card.second)) {
                                    Log.i("FlashcardViewModel.kt", "Changing ${card.second.unicode} priority ${Float.POSITIVE_INFINITY} to ${kanjiRepository.getRetentionFromKanji(card.second.unicode)}...")
                                    queue.add(Pair(kanjiRepository.getRetentionFromKanji(card.second.unicode), card.second))
                                    queue.remove(card)
                                }
                            }
                        }
                        if (queue.isEmpty()) {
                            Log.i("FlashcardViewModel.kt", "Empty queue...")
                            _state.update(
                                function = {
                                    it.copy(
                                        isReviewAvailable = false
                                    )
                                }
                            )
                        } else {
                            Log.i("FlashcardViewModel.kt", "Queue Refreshed: \n${queue.joinToString("\n")}")
                            _state.update(
                                function = {
                                    it.copy(
                                        isReviewAvailable = true,
                                        queue = queue
                                    )
                                }
                            )
                        }
                    }
                )
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
                        val i : Kanji? = state.value.queue.peek()?.second
                        if (i != null) {
                            Log.i("FlashcardViewModel.kt", "Peeking kanji ${i.unicode}...")
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

                    Log.i("FlashcardViewModel.kt", "Changing ${kanji.unicode} priority ${state.value.queue.peek()!!.first} to ${state.value.queue.peek()!!.first*2}...")
                    state.value.queue.add(Pair((state.value.queue.peek()!!.first)*2, kanji))
                    state.value.queue.remove()
                    onEvent(FlashcardEvent.GetRandomFlashcard)
                    onEvent(FlashcardEvent.RefreshQueue)

                }
            }
            is FlashcardEvent.CorrectCard -> {
                viewModelScope.launch {
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

                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.insertReview(
                        review = review
                    )

                    Log.i("FlashcardViewModel.kt", "Removing ${state.value.queue.peek()}...")
                    state.value.queue.remove()
                    onEvent(FlashcardEvent.GetRandomFlashcard)
                    onEvent(FlashcardEvent.RefreshQueue)
                }
            }
            is FlashcardEvent.EasyCard -> {
                viewModelScope.launch {
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

                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    kanjiRepository.insertReview(
                        review = review
                    )

                    Log.i("FlashcardViewModel.kt", "Removing ${state.value.queue.peek()}...")
                    state.value.queue.remove()
                    onEvent(FlashcardEvent.GetRandomFlashcard)

                    onEvent(FlashcardEvent.RefreshQueue)
                }
            }
        }
    }
}