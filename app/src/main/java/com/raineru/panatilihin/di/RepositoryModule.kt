package com.raineru.panatilihin.di

import com.raineru.panatilihin.data.NotesRepository
import com.raineru.panatilihin.data.OfflineNotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOfflineNotesRepository(offlineNotesRepository: OfflineNotesRepository) : NotesRepository
}