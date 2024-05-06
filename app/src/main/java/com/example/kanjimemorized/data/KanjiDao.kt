package com.example.kanjimemorized.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import com.example.kanjimemorized.data.entities.relations.ReviewsOfKanji
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface KanjiDao {

    @Upsert
    suspend fun upsertKanji(kanji: Kanji)

    @Upsert
    suspend fun insertKanjiComponent(kanjiComponent: KanjiComponentCrossRef)

    @Upsert
    suspend fun upsertReview(review: Review)

    @Query("SELECT * FROM kanji")
    suspend fun getKanjiList(): List<Kanji>

    @Query("SELECT * FROM kanji ORDER BY unicode ASC")
    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY strokes ASC")
    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>>

    @Query("SELECT * FROM kanji ORDER BY durability DESC")
    fun getKanjiOrderedByDurability(): Flow<List<Kanji>>

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


    @Query("SELECT * FROM review WHERE unicode = :kanji")
    suspend fun getReviewsFromKanji(kanji: Char): List<Review>

    @Query("SELECT B.unicode AS unicode, B.meanings AS meanings, B.strokes AS strokes, B.durability AS durability " +
            "FROM Kanji A, Kanji B, KanjiComponentCrossRef C " +
            "WHERE A.unicode = C.kanjiUnicode AND B.unicode = C.componentUnicode AND A.unicode = :kanji")
    suspend fun getKanjiComponentsFromKanji(kanji: Char): List<Kanji>

    @Query("SELECT durability FROM kanji WHERE unicode = :kanji")
    suspend fun getDurabilityFromKanji(kanji: Char): Float

    @Query("SELECT date FROM review WHERE unicode = :kanji AND rating > 1 ORDER BY date DESC LIMIT 1")
    suspend fun getLatestDateFromKanji(kanji: Char): String

  //  @Query("SELECT kanji.unicode as unicode, durability, MAX(date) as latest_date, CURRENT_TIMESTAMP as now " +
    //        "FROM review JOIN kanji WHERE kanji.unicode = '22909' AND rating > 1")
//    suspend fun calculateRetention(kanji: Char): Float

    @Query("DELETE FROM kanji")
    suspend fun deleteAllKanji()

    @Query("DELETE FROM kanjicomponentcrossref")
    suspend fun deleteAllKanjiComponent()

    @Query("DELETE FROM review")
    suspend fun deleteAllReviews()
}

//  Return a list of kanji where the kanji attributes and the attributes of the kanji defined by their decompositions follow certain conditions
//      Return a kanji where it's attributes and the attributes of the kanji defined by their decompositions follow certain conditions
//          Return the list of kanji defined by their decompositions and check if their attributes follow certain conditions
//              Return a kanji defined by their decompositions
//              Check if the kanji follows certain conditions
//          Return a kanji and check if it's attributes follow certain conditions