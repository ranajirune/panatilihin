package com.raineru.panatilihin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(

    val title: String,
    val content: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
