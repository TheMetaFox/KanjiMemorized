package com.example.kanjimemorized.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import com.example.kanjimemorized.data.entities.relations.KanjiMeaningCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface KanjiDao {

    @Upsert
    suspend fun upsertKanji(kanji: Kanji)

    @Insert
    suspend fun insertKanjiMeaning(kanjiMeaning: KanjiMeaningCrossRef)

    @Insert
    suspend fun insertKanjiComponent(kanjiComponent: KanjiComponentCrossRef)

    @Insert
    suspend fun insertReview(review: Review)

    @Query("SELECT * FROM kanji")
    suspend fun getKanjiList(): List<Kanji>

    @Query("SELECT * FROM kanji ORDER BY unicode ASC")
    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY strokes ASC")
    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY durability DESC")
    fun getKanjiOrderedByDurability(): Flow<List<Kanji>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY kanji.unicode ASC")
    fun getMeaningOrderedByUnicode(): Flow<List<String>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY strokes ASC")
    fun getMeaningOrderedByStrokes(): Flow<List<String>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY durability DESC, kanji.unicode ASC")
    fun getMeaningOrderedByDurability(): Flow<List<String>>

    @Query("SELECT MAX(date) as date FROM (" +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE kanji.unicode = NULL AND rating > 1) " +
            "GROUP BY unicode ORDER BY unicode ASC")
    fun getLatestDateOrderedByUnicode(): Flow<List<String>>

    @Query("SELECT MAX(date) as date FROM (" +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE kanji.unicode = NULL AND rating > 1) " +
            "GROUP BY unicode ORDER BY strokes ASC")
    fun getLatestDateOrderedByStrokes(): Flow<List<String>>

    @Query("SELECT MAX(date) as date FROM (" +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, date, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE kanji.unicode = NULL AND rating > 1) " +
            "GROUP BY unicode ORDER BY durability DESC")
    fun getLatestDateOrderedByDurability(): Flow<List<String>>

    @Query("SELECT meaning FROM kanjimeaningcrossref WHERE unicode = :kanji")
    suspend fun getMeaningsFromKanji(kanji: Char): List<String>

    @Query("SELECT * FROM review WHERE unicode = :kanji")
    suspend fun getReviewsFromKanji(kanji: Char): List<Review>

    @Query("SELECT B.unicode AS unicode, B.strokes AS strokes, B.durability AS durability " +
            "FROM Kanji A, Kanji B, KanjiComponentCrossRef C " +
            "WHERE A.unicode = C.kanjiUnicode AND B.unicode = C.componentUnicode AND A.unicode = :kanji")
    suspend fun getKanjiComponentsFromKanji(kanji: Char): List<Kanji>

    @Query("SELECT durability FROM kanji WHERE unicode = :kanji")
    suspend fun getDurabilityFromKanji(kanji: Char): Float

    @Query("SELECT date FROM review WHERE unicode = :kanji AND rating > 1 ORDER BY date DESC LIMIT 1")
    suspend fun getLatestDateFromKanji(kanji: Char): String

    @Query("DELETE FROM kanji")
    suspend fun deleteAllKanji()

    @Query("DELETE FROM kanjicomponentcrossref")
    suspend fun deleteAllKanjiComponent()

    @Query("DELETE FROM review")
    suspend fun deleteAllReviews()
}