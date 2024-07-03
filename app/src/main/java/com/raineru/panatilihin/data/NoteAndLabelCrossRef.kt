package com.raineru.panatilihin.data

import androidx.room.Entity

@Entity(
    tableName = "note_label_cross_ref",
    primaryKeys = ["noteId", "labelId"]
)
data class NoteAndLabelCrossRef(
    val noteId: Long,
    val labelId: Long
)