package com.raineru.panatilihin2.di

import android.content.Context
import androidx.room.Room
import com.raineru.panatilihin2.data.NoteDao
import com.raineru.panatilihin2.data.PanatilihinDatabase
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
    @Singleton
    fun provideNoteDao(database: PanatilihinDatabase): NoteDao = database.noteDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PanatilihinDatabase =
        Room.databaseBuilder(
            context,
            PanatilihinDatabase::class.java,
            "panatilihin_database"
        )
            .fallbackToDestructiveMigration()
            .build()
}