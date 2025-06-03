package com.example.kanjimemorized

import android.content.Context
import androidx.room.Room
import com.example.kanjimemorized.data.KanjiDao
import com.example.kanjimemorized.data.KanjiDatabase
import com.example.kanjimemorized.data.KanjiRepository
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
private lateinit var kanjiDatabase: KanjiDatabase
    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): KanjiDatabase {
         kanjiDatabase = Room
            .databaseBuilder(
                context = context,
                klass = KanjiDatabase::class.java,
                name = "Kanji.db"
            )
            .createFromAsset("Kanji.db")
            .addMigrations(KanjiDatabase.migration1to2, KanjiDatabase.migration2to3, KanjiDatabase.migration3to4)
            .build()
        return kanjiDatabase
    }

    @Singleton
    @Provides
    fun provideDao(
        database: KanjiDatabase
    ) = database.kanjiDao

    @Singleton
    @Provides
    fun provideRepository(
        dao: KanjiDao
    ) = KanjiRepository(dao)
}