package com.example.kanjimemorized

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeogramDao {

    @Insert
    suspend fun insertIdeogram(ideogram: Ideogram)

    @Delete
    suspend fun deleteIdeogram(ideogram: Ideogram)

    @Query("SELECT * FROM ideogram ORDER BY id ASC")
    fun getIdeogramOrderedById(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY strokes ASC")
    fun getIdeogramOrderedByStrokes(): Flow<List<Ideogram>>

    @Query("SELECT * FROM ideogram ORDER BY retention ASC")
    fun getIdeogramOrderedByRetention(): Flow<List<Ideogram>>
}