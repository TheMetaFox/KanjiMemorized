package com.example.kanjimemorized.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Ideogram::class],
    version = 1,
)
abstract class IdeogramDatabase: RoomDatabase() {
    abstract val dao: IdeogramDao
}