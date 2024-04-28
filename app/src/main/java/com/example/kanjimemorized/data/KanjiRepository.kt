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
import kotlin.math.exp

class KanjiRepository(private val kanjiDao: KanjiDao) {
    suspend fun initializeKanjiData() {
        kanjiDao.deleteAllKanji()
        kanjiDao.deleteAllKanjiComponent()
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
            parse(kanjiDao.getLatestDateFromKanji(kanji = kanji)),
            LocalDateTime.now()
        ).toMinutes()).toDouble()
        return exp(-((minutes/1440) / durability).toFloat())
    }
    suspend fun getRandomKanji(): Kanji {
        return kanjiDao.getKanjiList().random()
    }

    /*
    suspend fun getRandomStudyableKanji(): Kanji {
        return kanjiDao.getStudyableKanjiList().random()
    }


    fun filterNonStudyableFromKanjiList( kanji: List<Kanji>): Flow<List<Kanji>> {
        var filteredKanji: List<Kanji>
        kanji.forEach { }
    }
    */
    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByUnicode()
    }
    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByStrokes()
    }
    fun getKanjiOrderedByDurability(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByDurability()
    }
    fun getLatestDateOrderedByUnicode(): Flow<List<LocalDateTime>> {
        return kanjiDao.getLatestDateOrderedByUnicode()
    }
    fun getLatestDateOrderedByStrokes(): Flow<List<LocalDateTime>> {
        return kanjiDao.getLatestDateOrderedByStrokes()
    }
    fun getLatestDateOrderedByDurability(): Flow<List<LocalDateTime>> {
        return kanjiDao.getLatestDateOrderedByDurability()
    }


    suspend fun getRetentionOrderedByUnicode(): Flow<List<Float>> {
        val retention: List<Float> = listOf()
        val kanjiList: Flow<List<Kanji>> = kanjiDao.getKanjiOrderedByUnicode()
        kanjiList.collect { list ->
            list.forEach {
                retention.plus(getRetentionFromKanji(it.unicode))
            }
        }
        Log.e("Log.INFO","\n\n\n"+retention+"\n\n\n")
        return flowOf(retention)
    }
    suspend fun getKanjiRetentionOrderedByUnicode(): Flow<List<Pair<Kanji, Float>>> {
        val kanji: List<Kanji> = listOf()
        val retention: List<Float> = listOf()
        val kanjiList: Flow<List<Kanji>> = kanjiDao.getKanjiOrderedByUnicode()
        kanjiList.collect { list ->
            list.forEach {
                kanji.plus(it)
                retention.plus(getRetentionFromKanji(it.unicode))
            }
        }
        Log.e("Log.INFO","\n\n\n"+retention+"\n\n\n")
        return flowOf(kanji.zip(retention))
    }
    //suspend fun getStudyableKanjiOrderedByUnicode(): Flow<List<Kanji>> {
    //    var kanjiList: List<Kanji> = kanjiDao.getKanjiOrderedByUnicode()
    //}
}