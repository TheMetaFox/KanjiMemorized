package com.example.kanjimemorized.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import com.example.kanjimemorized.data.entities.relations.KanjiMeaningCrossRef

@Database(
    entities = [Kanji::class, Review::class, KanjiMeaningCrossRef::class, KanjiComponentCrossRef::class, Settings::class],
    version = 1,
    exportSchema = true,
)
abstract class KanjiDatabase: RoomDatabase() {
    abstract val kanjiDao: KanjiDao
}