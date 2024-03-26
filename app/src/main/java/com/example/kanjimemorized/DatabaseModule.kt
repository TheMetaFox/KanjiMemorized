package com.example.kanjimemorized

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kanjimemorized.KanjiData.KanjiData
import com.example.kanjimemorized.ComponentData.ComponentData
import com.example.kanjimemorized.data.KanjiDao
import com.example.kanjimemorized.data.KanjiDatabase
import com.example.kanjimemorized.data.KanjiRepository
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            .addCallback(
                callback = object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        provideKanjiData(KanjiRepository(kanjiDatabase.kanjiDao))
                        provideKanjiComponentData(KanjiRepository(kanjiDatabase.kanjiDao))
                    }
                })
            .fallbackToDestructiveMigration()
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


    @Singleton
    @Provides
    fun provideKanjiData(kanjiRepository: KanjiRepository) {
        CoroutineScope(Dispatchers.IO).launch() {
            KanjiData.forEach {kanji ->
                kanjiRepository.upsertKanji(
                    kanji
                )
            }
        }
    }

    @Singleton
    @Provides
    fun provideKanjiComponentData(kanjiRepository: KanjiRepository) {
        CoroutineScope(Dispatchers.IO).launch() {
            ComponentData.forEach { component ->
                kanjiRepository.insertKanjiComponent(
                    component
                )
            }
        }
    }
}