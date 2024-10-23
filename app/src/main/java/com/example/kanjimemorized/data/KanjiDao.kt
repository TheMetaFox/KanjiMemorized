package com.example.kanjimemorized.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import com.example.kanjimemorized.data.entities.relations.KanjiMeaningCrossRef
import kotlinx.coroutines.flow.Flow
import java.util.PriorityQueue

@Dao
interface KanjiDao {

    @Upsert
    suspend fun upsertKanji(kanji: Kanji): Void

    @Insert
    suspend fun insertReview(review: Review): Void

    @Query("UPDATE settings SET setValue = :setValue WHERE code = :code")
    suspend fun updateSettings(code: String, setValue: String): Void

    @Query("SELECT * FROM settings WHERE code = :code")
    suspend fun getSettingsFromCode(code: String): Settings

    @Query("SELECT * FROM kanji")
    suspend fun getKanjiList(): List<Kanji>

    @Query("SELECT * FROM kanji ORDER BY unicode ASC")
    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY strokes ASC, unicode ASC")
    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY durability DESC, unicode ASC")
    fun getKanjiOrderedByDurability(): Flow<List<Kanji>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY kanji.unicode ASC")
    fun getMeaningOrderedByUnicode(): Flow<List<String>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY strokes ASC, kanji.unicode ASC")
    fun getMeaningOrderedByStrokes(): Flow<List<String>>

    @Query("SELECT GROUP_CONCAT(meaning) as meaning FROM kanji " +
            "JOIN kanjimeaningcrossref ON kanji.unicode = kanjimeaningcrossref.unicode " +
            "GROUP BY kanji.unicode ORDER BY durability DESC, kanji.unicode ASC")
    fun getMeaningOrderedByDurability(): Flow<List<String>>

    @Query("SELECT MAX(datetime) as datetime FROM (" +
            "SELECT kanji.unicode, strokes, durability, datetime, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE rating > 1 " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, datetime = null, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE review.unicode IS NULL OR rating = 1) " +
            "GROUP BY unicode ORDER BY unicode ASC")
    fun getLatestDateOrderedByUnicode(): Flow<List<String>>

    @Query("SELECT MAX(datetime) as datetime FROM (" +
            "SELECT kanji.unicode, strokes, durability, datetime, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE rating > 1 " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, datetime = null, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE review.unicode IS NULL OR rating = 1) " +
            "GROUP BY unicode ORDER BY strokes ASC, unicode ASC")
    fun getLatestDateOrderedByStrokes(): Flow<List<String>>

    @Query("SELECT MAX(datetime) as datetime FROM (" +
            "SELECT kanji.unicode, strokes, durability, datetime, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE rating > 1 " +
            "UNION ALL " +
            "SELECT kanji.unicode, strokes, durability, datetime = null, rating FROM kanji LEFT JOIN review ON review.unicode = kanji.unicode " +
            "WHERE review.unicode IS NULL OR rating = 1) " +
            "GROUP BY unicode ORDER BY durability DESC, unicode ASC")
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

    @Query("SELECT datetime FROM review WHERE unicode = :kanji AND rating > 1 ORDER BY datetime DESC LIMIT 1")
    suspend fun getLatestDateFromKanji(kanji: Char): String

    @Query("SELECT count(unicode) FROM (" +
            "SELECT MIN(datetime) as datetime, unicode, rating " +
            "FROM review WHERE rating > 1 GROUP BY unicode) " +
            "WHERE substr(datetime, 1, 10) = :today")
    suspend fun getEarliestDateCountFromToday(today: String): Int

    @Query("UPDATE kanji SET durability = 0.0")
    suspend fun resetKanjiData()

    @Query("DELETE FROM review")
    suspend fun deleteAllReviews()
}