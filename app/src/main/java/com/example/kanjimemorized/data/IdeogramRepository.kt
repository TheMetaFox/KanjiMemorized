package com.example.kanjimemorized.data

import kotlinx.coroutines.flow.Flow

class IdeogramRepository(private val ideogramDao: IdeogramDao) {
    suspend fun insertIdeogram(ideogram: Ideogram) {
        ideogramDao.insertIdeogram(
            ideogram = ideogram)
    }
    suspend fun deleteIdeogram(ideogram: Ideogram) {
        ideogramDao.deleteIdeogram(
            ideogram = ideogram)
    }
    fun getIdeogramListOrderedByUnicode(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramOrderedByUnicode()
    }
    fun getIdeogramListOrderedByStrokes(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramOrderedByStrokes()
    }
    fun getIdeogramListOrderedByRetention(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramOrderedByRetention()
    }
}