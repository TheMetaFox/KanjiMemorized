package com.example.kanjimemorized.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef

@Database(
    entities = [Kanji::class, Review::class, KanjiComponentCrossRef::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    value = [Converter::class]
)
abstract class KanjiDatabase: RoomDatabase() {
    abstract val kanjiDao: KanjiDao
}