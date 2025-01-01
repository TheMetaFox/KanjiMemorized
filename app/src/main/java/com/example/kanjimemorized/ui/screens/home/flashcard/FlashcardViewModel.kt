package com.example.kanjimemorized.ui.screens.home.flashcard

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
                _state.update(
                    function = {
                        it.copy(
                            isLoading = true,
                            isAnswerShowing = false
                        )
                    }
                )
                viewModelScope.launch {
                    when(state.value.studyType) {
                        StudyType.NEW -> {
                            Log.i("FlashcardViewModel.kt", "Initializing queue with study type New...")
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
                        }
                        StudyType.REVIEW -> {
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
                        }
                        StudyType.MIXED -> {
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
                        }
                    }
                    onEvent(FlashcardEvent.GetRandomFlashcard)
                    _state.update(
                        function = {
                            it.copy(
                                isLoading = false
                            )
                        }
                    )
                }
            }
            is FlashcardEvent.RefreshQueue -> {
                Log.i("FlashcardViewModel.kt", "Refreshing queue...")
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
                        Log.i("FlashcardViewModel.kt", "Kanji animation ending...")
                        if (queue.isEmpty()) {
                            Log.i("FlashcardViewModel.kt", "Empty queue...")
                            _state.update(
                                function = {
                                    it.copy(
                                        isReviewAvailable = false,
                                        isAnimationPlaying = false
                                    )
                                }
                            )
                        } else {
//                            Log.i("FlashcardViewModel.kt", "Queue Refreshed: \n${queue.joinToString("\n")}")
                            _state.update(
                                function = {
                                    it.copy(
                                        isReviewAvailable = true,
                                        queue = queue,
                                        isAnimationPlaying = false
                                    )
                                }
                            )
                        }
                        onEvent(FlashcardEvent.GetRandomFlashcard)
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
                Log.i("FlashcardViewModel.kt", "${when (state.value.isAnswerShowing) {true -> {"Showing"} false -> {"Hiding"}}} answer...")
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
            is FlashcardEvent.ProcessCard -> {
                viewModelScope.launch {
                    Log.i("FlashcardViewModel.kt", "Processing ${state.value.kanji} with ${flashcardEvent.rating} rating...")
                    var ease: Float = if (state.value.kanji!!.durability == 0f) kanjiRepository.getSettingsFromCode("initial_ease").setValue.toFloat() else state.value.kanji!!.ease
                    if (flashcardEvent.rating == Rating.EASY) {
                        ease += 0.1f
                    }
                    if (flashcardEvent.rating == Rating.WRONG) {
                        ease -= 0.32f
                        ease = if (ease <= 1.3f) 1.3f else ease
                    }
                    var durability: Float = if (state.value.kanji!!.durability == 0f) 1f else state.value.kanji!!.durability
                    if (flashcardEvent.rating == Rating.EASY) {
                        durability *= 1.2f
                    }
                    if (flashcardEvent.rating == Rating.WRONG) {
                        durability *= 0.5f
                        durability = if (durability <= 0.1f) 0f else durability
                    } else {
                        durability *= ease
                    }

                    val kanji = Kanji(
                        unicode = state.value.kanji!!.unicode,
                        strokes = state.value.kanji!!.strokes,
                        durability = durability,
                        ease = ease
                    )

                    val review = Review(
                        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        unicode = state.value.kanji!!.unicode,
                        rating = when (flashcardEvent.rating) { Rating.WRONG -> 1; Rating.CORRECT -> 2; Rating.EASY -> 3 }
                    )

                    Log.i("FlashcardViewModel.kt", "Updating ${kanji.unicode} in database...")
                    kanjiRepository.upsertKanji(
                        kanji = kanji
                    )
                    Log.i("FlashcardViewModel.kt", "Inserting review (${review.unicode}, ${review.datetime}, ${review.rating}) in database...")
                    kanjiRepository.insertReview(
                        review = review
                    )

                    if (flashcardEvent.rating == Rating.WRONG) {
                        state.value.queue.add(Pair((state.value.queue.peek()!!.first)*2, kanji))
                        Log.i("FlashcardViewModel.kt", "Changing ${kanji.unicode} priority ${state.value.queue.peek()!!.first} to ${state.value.queue.peek()!!.first*2}...")

                    }
                    Log.i("FlashcardViewModel.kt", "Processed ${state.value.queue.peek()!!.second}...")
                    state.value.queue.remove()
                    onEvent(FlashcardEvent.RefreshQueue)

                    _state.update(
                        function = {
                            it.copy(lastRating = when (flashcardEvent.rating) { Rating.WRONG -> 1; Rating.CORRECT -> 2; Rating.EASY -> 3 })
                        }
                    )
                }
            }
            is FlashcardEvent.PlayAnimation -> {
                Log.i("FlashcardViewModel.kt", "Kanji animation starting...")
                _state.update(
                    function = {
                        it.copy(isAnimationPlaying = true)
                    }
                )
            }
        }
    }
}