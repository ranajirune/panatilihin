package com.raineru.panatilihin.di

import android.content.Context
import androidx.room.Room
import com.raineru.panatilihin.data.NoteDao
import com.raineru.panatilihin.data.PanatilihinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideNoteDao(panatilihinDatabase: PanatilihinDatabase) : NoteDao {
        return panatilihinDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PanatilihinDatabase {
        return Room.databaseBuilder(
            appContext,
            PanatilihinDatabase::class.java,
            "panatilihin_database"
        )
            .build()
    }
}