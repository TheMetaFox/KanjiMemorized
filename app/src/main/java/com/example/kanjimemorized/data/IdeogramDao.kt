package com.example.kanjimemorized.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeogramDao {

    @Upsert
    suspend fun insertIdeogram(ideogram: Ideogram)

    @Query("SELECT * FROM ideogram")
    suspend fun getIdeogramList(): List<Ideogram>

    @Query("SELECT * FROM ideogram ORDER BY unicode ASC")
    fun getIdeogramListOrderedByUnicode(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY strokes ASC")
    fun getIdeogramListOrderedByStrokes(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY retention ASC")
    fun getIdeogramListOrderedByRetention(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram WHERE unicode IN (:decompositions)")
    suspend fun getIdeogramDecompositionsList(decompositions: List<Char>): List<Ideogram>
}