package com.example.kanjimemorized

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.kanjimemorized.IdeogramData.IdeogramData
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideIdeogramData(ideogramRepository: IdeogramRepository) {
        CoroutineScope(Dispatchers.IO).launch() {
            IdeogramData.forEach {ideogram ->
                ideogramRepository.insertIdeogram(
                    ideogram
                )
            }
        }
    }
}