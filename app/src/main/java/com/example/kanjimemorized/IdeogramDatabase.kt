package com.example.kanjimemorized

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [Ideogram::class],
    version = 1,
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2, spec = IdeogramDatabase.MyAutoMigration::class)
//    ]

)
abstract class IdeogramDatabase: RoomDatabase() {
//    @RenameColumn(tableName = "Ideogram", fromColumnName = "id", toColumnName = "unicode")
    class MyAutoMigration : AutoMigrationSpec

    abstract val dao: IdeogramDao
}