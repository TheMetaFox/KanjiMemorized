package com.example.kanjimemorized.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import com.example.kanjimemorized.data.entities.relations.KanjiMeaningCrossRef

@Database(
    entities = [Kanji::class, Review::class, KanjiMeaningCrossRef::class, KanjiComponentCrossRef::class, Settings::class],
    version = 3,
    exportSchema = true,
)
abstract class KanjiDatabase: RoomDatabase() {
    abstract val kanjiDao: KanjiDao

    companion object {
        val migration1to2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS `New_Settings` (
                            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                            `code` TEXT NOT NULL, 
                            `setValue` TEXT NOT NULL, 
                            `defaultValue` TEXT NOT NULL
                        );
                    """.trimIndent()
                )
                db.execSQL(
                    """
                        DROP TABLE `Settings`;
                    """.trimIndent()
                )
                db.execSQL(
                    """
                        ALTER TABLE `New_Settings` RENAME TO `Settings`;
                    """.trimIndent()
                )
            }
        }
        val migration2to3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS `New_Settings` (
                            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                            `code` TEXT NOT NULL, 
                            `setValue` TEXT NOT NULL, 
                            `defaultValue` TEXT NOT NULL
                        );
                    """.trimIndent()
                )
                db.execSQL(
                    """
                        DROP TABLE `Settings`;
                    """.trimIndent()
                )
                db.execSQL(
                    """
                        ALTER TABLE `New_Settings` RENAME TO `Settings`;
                    """.trimIndent()
                )
            }
        }
    }
}