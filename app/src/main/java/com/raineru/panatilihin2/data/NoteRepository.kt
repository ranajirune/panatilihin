package com.raineru.panatilihin2.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNote(id: Long): Flow<Note?>

    suspend fun insertNote(note: Note)
}