package com.raineru.panatilihin.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineNoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteLabelDao: NoteLabelDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getAllNotes(query: String): Flow<List<Note>> = noteDao.getAllNotes(query)

    override fun getAllNotes(labelId: Long): Flow<List<Note>> {
        TODO("return all notes associated with a label")
    }

    override fun getNote(id: Long): Flow<Note?> = noteDao.getNote(id)

    override suspend fun insertNote(item: Note): Long = noteDao.insert(item)

    override suspend fun deleteNote(item: Note) = noteDao.delete(item)

    override suspend fun deleteNote(id: Long) = noteDao.delete(id)

    override suspend fun updateNote(item: Note) = noteDao.update(item)

    override fun getNotesTitleAndContent(): Flow<List<NoteAndContent>> =
        noteDao.getNoteTitleAndContents()

    override fun getNoteLabels(): Flow<List<NoteLabels>> = noteDao.getNoteLabels()

    override suspend fun insertNotes(notes: List<Note>) = noteDao.insertNotes(notes)

    override suspend fun insertNoteLabel(noteAndLabelCrossRef: NoteAndLabelCrossRef) =
        noteDao.insertNoteLabel(noteAndLabelCrossRef)

    override suspend fun insertNoteLabel(noteAndLabelCrossRef: List<NoteAndLabelCrossRef>) =
        noteDao.insertNoteLabel(noteAndLabelCrossRef)

    override suspend fun deleteNoteLabel(noteAndLabelCrossRef: NoteAndLabelCrossRef) =
        noteLabelDao.deleteNoteLabel(noteAndLabelCrossRef)

    override fun getNoteLabels(id: Long): Flow<NoteLabels?> = noteDao.getNoteLabels(id)

    override suspend fun insertLabelWithRef(label: Label, noteId: Long) =
        noteDao.insertLabelWithRef(label, noteId)
}