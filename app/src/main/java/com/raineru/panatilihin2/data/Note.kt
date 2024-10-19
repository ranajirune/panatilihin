package com.raineru.panatilihin2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "notes"
)
data class Note(
    val title: String,
    val content: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)