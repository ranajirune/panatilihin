package com.raineru.panatilihin2.di

import com.raineru.panatilihin2.data.NoteRepository
import com.raineru.panatilihin2.data.OfflineNoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOfflineNotesRepository(
        offlineNoteRepository: OfflineNoteRepository
    ): NoteRepository
}