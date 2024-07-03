package com.raineru.panatilihin.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>

    fun getAllNotes(query: String): Flow<List<Note>>

    fun getAllNotes(labelId: Long): Flow<List<Note>>

    fun getNote(id: Long): Flow<Note?>

    suspend fun insertNote(item: Note): Long

    suspend fun deleteNote(item: Note)

    suspend fun updateNote(item: Note)

    suspend fun deleteNote(id: Long)

    fun getNotesTitleAndContent(): Flow<List<NoteAndContent>>

    fun getNoteLabels(): Flow<List<NoteLabels>>

    fun getNoteLabels(id: Long): Flow<NoteLabels?>

    suspend fun insertNotes(notes: List<Note>)

    suspend fun insertNoteLabel(noteAndLabelCrossRef: NoteAndLabelCrossRef)

    suspend fun insertNoteLabel(noteAndLabelCrossRef: List<NoteAndLabelCrossRef>)

    suspend fun deleteNoteLabel(noteAndLabelCrossRef: NoteAndLabelCrossRef)

    suspend fun insertLabelWithRef(label: Label, noteId: Long)
}