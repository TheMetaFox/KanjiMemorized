package com.example.kanjimemorized

import android.content.Context
import androidx.room.Room
import com.example.kanjimemorized.IdeogramData.radicalIdeogramData
import com.example.kanjimemorized.data.Ideogram
import com.example.kanjimemorized.data.IdeogramDao
import com.example.kanjimemorized.data.IdeogramDatabase
import com.example.kanjimemorized.data.IdeogramRepository
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = IdeogramDatabase::class.java,
        name = "Ideogram.db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(
        database: IdeogramDatabase
    ) = database.ideogramDao

    @Singleton
    @Provides
    fun provideRepository(
        dao: IdeogramDao
    ) = IdeogramRepository(dao)

    @Singleton
    @Provides
    fun provideIdeogramData(ideogramRepository: IdeogramRepository) {
        CoroutineScope(Dispatchers.IO).launch() {
            radicalIdeogramData.forEach {SimpleIdeogram ->
                ideogramRepository.insertIdeogram(
                    Ideogram(
                        unicode = Integer.parseInt(SimpleIdeogram.unicode, 16).toChar(),
                        meanings = SimpleIdeogram.meanings,
                        strokes = SimpleIdeogram.strokes
                    )
                )
            }
        }
    }
}