package com.raineru.panatilihin2.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class PanatilihinDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}