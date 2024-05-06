package com.example.kanjimemorized.data

import android.util.Log
import com.example.kanjimemorized.KanjiData.KanjiData
import com.example.kanjimemorized.ComponentData.ComponentData
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter
import kotlin.math.exp

class KanjiRepository(private val kanjiDao: KanjiDao) {
    suspend fun initializeKanjiData() {
        kanjiDao.deleteAllKanji()
        kanjiDao.deleteAllKanjiComponent()
        kanjiDao.deleteAllReviews()
        KanjiData.forEach {
            kanjiDao.upsertKanji(
                kanji = it
            )
        }
        ComponentData.forEach {
            kanjiDao.insertKanjiComponent(
                kanjiComponent = it
            )
        }
    }

    suspend fun upsertKanji(kanji: Kanji) {
        kanjiDao.upsertKanji(
            kanji = kanji
        )
    }

    suspend fun upsertReview(review: Review) {
        kanjiDao.upsertReview(
            review = review
        )
    }

    suspend fun insertKanjiComponent(kanjiComponent: KanjiComponentCrossRef) {
        kanjiDao.insertKanjiComponent(
            kanjiComponent = kanjiComponent
        )
    }

    suspend fun getKanjiComponentsFromKanji(kanji: Char): List<Kanji> {
        return kanjiDao.getKanjiComponentsFromKanji(
            kanji = kanji
        )
    }

    suspend fun getKanjiComponentsLatestDatesFromKanji(kanji: Char): List<LocalDateTime?> {
        var list = listOf<LocalDateTime?>()
        kanjiDao.getKanjiComponentsFromKanji(
            kanji = kanji
        ).forEach {
            Log.i("KanjiRepository.kt", "Getting kanji component latest date:$it")
            list = list.plus(getLatestDateFromKanji(it.unicode))
        }
        Log.i("KanjiRepository.kt", "Kanji components' latest date list:$list")
        return list
    }

    suspend fun getReviewsFromKanji(kanji: Char): List<Review> {
        return kanjiDao.getReviewsFromKanji(
            kanji = kanji
        )
    }

    suspend fun getRetentionFromKanji(kanji: Char): Float {
        val durability: Int = kanjiDao.getDurabilityFromKanji(kanji = kanji).toInt()
        if (durability == 0) {
            return 0f
        }
        val minutes: Double = (Duration.between(
            getLatestDateFromKanji(kanji = kanji),
            LocalDateTime.now()
        ).toMinutes()).toDouble()
        val retention = exp(-((minutes / 1440) / durability).toFloat())
        Log.i("KanjiRepository.kt", "Calculating retention with $minutes minutes and $durability durability: $retention")
        return retention
    }

    suspend fun getLatestDateFromKanji(kanji: Char): LocalDateTime? {
        var date: String = kanjiDao.getLatestDateFromKanji(kanji = kanji)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        if (date.isNullOrEmpty()) date = ""
        Log.i("KanjiRepository.kt", "Latest review date of $kanji is $date")
        return if (date.isNullOrEmpty()) null else parse(date, formatter)
    }

    suspend fun getRandomKanji(): Kanji {
        return kanjiDao.getKanjiList().random()
    }

    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByUnicode()
    }

    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByStrokes()
    }

    fun getKanjiOrderedByDurability(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByDurability()
    }

    fun getLatestDateOrderedByUnicode(): Flow<List<String>> {
        return kanjiDao.getLatestDateOrderedByUnicode()
    }

    fun getLatestDateOrderedByStrokes(): Flow<List<String>> {
        return kanjiDao.getLatestDateOrderedByStrokes()
    }

    fun getLatestDateOrderedByDurability(): Flow<List<String>> {
        return kanjiDao.getLatestDateOrderedByDurability()
    }
}