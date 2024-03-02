package com.raineru.panatilihin.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineNotesRepository @Inject constructor(private val noteDao: NoteDao) : NotesRepository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNote(id: Int): Flow<Note?> = noteDao.getNote(id)

    override suspend fun insertNote(item: Note) = noteDao.insert(item)

    override suspend fun deleteNote(item: Note) = noteDao.delete(item)

    override suspend fun updateNote(item: Note) = noteDao.update(item)
}