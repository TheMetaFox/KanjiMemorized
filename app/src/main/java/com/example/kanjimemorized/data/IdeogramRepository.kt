package com.example.kanjimemorized.data

import kotlinx.coroutines.flow.Flow

class IdeogramRepository(private val ideogramDao: IdeogramDao) {
    suspend fun insertIdeogram(ideogram: Ideogram) {
        ideogramDao.insertIdeogram(
            ideogram = ideogram
        )
    }
    suspend fun updateIdeogram(ideogram: Ideogram) {
        ideogramDao.updateIdeogram(
            ideogram = ideogram
        )
    }
    suspend fun getRandomIdeogram(): Ideogram {
        return ideogramDao.getIdeogramList().random()
    }
    suspend fun getRandomStudyableIdeogram(): Ideogram {
        return ideogramDao.getStudyableIdeogramList().random()
    }
    fun getIdeogramListOrderedByUnicode(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramListOrderedByUnicode()
    }
    fun getIdeogramListOrderedByStrokes(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramListOrderedByStrokes()
    }
    fun getIdeogramListOrderedByRetention(): Flow<List<Ideogram>> {
        return ideogramDao.getIdeogramListOrderedByRetention()
    }
    suspend fun getIdeogramDecompositionsList( decompositions: List<Char>): List<Ideogram> {
        return ideogramDao.getIdeogramDecompositionsList(
            decompositions = decompositions
        )
    }
}