package com.raineru.panatilihin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: Label): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteLabel(noteAndLabelCrossRef: NoteAndLabelCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteLabel(noteLabelCrossRefs: List<NoteAndLabelCrossRef>)

    @Transaction
    suspend fun insertLabelWithRef(label: Label, noteId: Long) {
        val labelId = insertLabel(label)
        insertNoteLabel(NoteAndLabelCrossRef(
            noteId = noteId,
            labelId = labelId
        ))
    }

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from notes WHERE id = :id")
    fun getNote(id: Long): Flow<Note?>

    @Query("SELECT * from notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("""
SELECT * from notes
WHERE title LIKE '%' || :query || '%'
OR content LIKE '%' || :query || '%'
ORDER BY id DESC
""")
    fun getAllNotes(query: String): Flow<List<Note>>

    @Query("DELETE FROM notes WHERE id IN (:ids)")
    suspend fun delete(ids: List<Long>)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT title, content FROM notes")
    fun getNoteTitleAndContents(): Flow<List<NoteAndContent>>

    @Transaction
    @Query("SELECT * FROM notes")
    fun getNoteLabels(): Flow<List<NoteLabels>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteLabels(id: Long): Flow<NoteLabels?>
}

val defaultNotes = listOf(
    Note("A Title 1", "A Content 1", id = 1),
    Note("A Title 2", "A Content 2", id = 2),
    Note("A Title 3", "A Content 3", id = 3),
    Note("A Title 4", "A Content 4", id = 4),
)

val defaultNoteAndLabelCrossRef = listOf(
    NoteAndLabelCrossRef(noteId = 1, labelId = 1),
    NoteAndLabelCrossRef(noteId = 1, labelId = 2),
    NoteAndLabelCrossRef(noteId = 2, labelId = 1),
    NoteAndLabelCrossRef(noteId = 2, labelId = 2),
    NoteAndLabelCrossRef(noteId = 2, labelId = 3),
    NoteAndLabelCrossRef(noteId = 3, labelId = 4),
    NoteAndLabelCrossRef(noteId = 4, labelId = 1),
)