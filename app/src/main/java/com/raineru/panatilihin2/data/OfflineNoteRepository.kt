package com.raineru.panatilihin2.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineNoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNote(id: Long): Flow<Note?> = noteDao.getNote(id)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)
}