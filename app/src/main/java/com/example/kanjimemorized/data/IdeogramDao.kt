package com.example.kanjimemorized.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeogramDao {

    @Upsert
    suspend fun insertIdeogram(ideogram: Ideogram)

    @Update
    suspend fun updateIdeogram(ideogram: Ideogram)

    @Query("SELECT * FROM ideogram")
    suspend fun getIdeogramList(): List<Ideogram>

    @Query("SELECT * FROM ideogram WHERE retention >= 0.9 OR decompositions = ''")
    suspend fun getStudyableIdeogramList(): List<Ideogram>

    @Query("SELECT * FROM ideogram ORDER BY unicode ASC")
    fun getIdeogramListOrderedByUnicode(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY strokes ASC")
    fun getIdeogramListOrderedByStrokes(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY retention DESC")
    fun getIdeogramListOrderedByRetention(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram WHERE unicode IN (:decompositions)")
    suspend fun getIdeogramDecompositionsList(decompositions: List<Char>): List<Ideogram>
}