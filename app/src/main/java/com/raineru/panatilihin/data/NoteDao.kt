package com.raineru.panatilihin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from notes WHERE id = :id")
    fun getNote(id: Long): Flow<Note?>

    @Query("SELECT * from notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM notes WHERE id IN (:ids)")
    suspend fun delete(ids: List<Long>)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Long)
}