package com.example.kanjimemorized.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Ideogram::class],
    version = 2,
)
@TypeConverters(Converter::class)
abstract class IdeogramDatabase: RoomDatabase() {
    abstract val dao: IdeogramDao
}