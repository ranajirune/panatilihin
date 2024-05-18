package com.raineru.panatilihin.data

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>

    fun getNote(id: Long): Flow<Note?>

    suspend fun insertNote(item: Note): Long

    suspend fun deleteNote(item: Note)

    suspend fun updateNote(item: Note)

    suspend fun deleteNote(id: Long)
}