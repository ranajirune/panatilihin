package com.raineru.panatilihin.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "notes")
data class Note(

    val title: String,
    val content: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)

data class NoteLabels(
    @Embedded
    val note: Note,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteAndLabelCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "labelId"
        )
    )
    val labels: List<Label>
)

