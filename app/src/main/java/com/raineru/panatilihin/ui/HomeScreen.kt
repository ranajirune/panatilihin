package com.raineru.panatilihin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.copy
import com.raineru.panatilihin.data.Note

@Composable
fun HomeScreen(
    notes: List<Note>,
    selectedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
) {
    NoteList(
        notes = notes,
        selectedNotes = selectedNotes,
        onNoteClick = onNoteClick,
        onNoteLongPress = onNoteLongPress,
        modifier = modifier,
        contentPadding = contentPadding
    )
}

@Composable
fun NoteList(
    notes: List<Note>,
    selectedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding.copy(
            start = 8.dp,
            end = 8.dp
        ),
        modifier = modifier
    ) {
        items(notes) {
            NoteEntry(
                note = it,
                isSelected = it.id in selectedNotes,
                onNoteClick = onNoteClick,
                onNoteLongPress = onNoteLongPress
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NoteListPreview(
    @PreviewParameter(NotePreviewParameterProvider::class)
    notes: List<Note>
) {
    NoteList(
        notes = notes,
        selectedNotes = listOf(3L, 5L, 7L),
        onNoteClick = {},
        onNoteLongPress = {}
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteEntry(
    note: Note,
    isSelected: Boolean,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                border = if (isSelected) {
                    BorderStroke(
                        3.dp,
                        SolidColor(Color(0xFF116682))
                    )
                } else {
                    BorderStroke(
                        1.dp,
                        SolidColor(Color(0xFFC0C8CD))
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = { onNoteClick(note.id) },
                onLongClick = { onNoteLongPress(note.id) }
            )
            .padding(18.dp),
    ) {
        if (note.title.isNotEmpty()) {
            Text(
                text = note.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = note.content,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NoteEntryWithoutTitlePreview() {
    val noteList = stringArrayResource(id = R.array.content_sample_list)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        NoteEntry(
            note = Note(
                title = "",
                content = noteList[1]
            ),
            isSelected = true,
            modifier = Modifier.align(Alignment.Center),
            onNoteClick = {},
            onNoteLongPress = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NoteEntryWithTitlePreview() {
    val noteList = stringArrayResource(id = R.array.content_sample_list)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        NoteEntry(
            note = Note(
                title = "This is the title you m....",
                content = noteList[1]
            ),
            isSelected = false,
            modifier = Modifier.align(Alignment.Center),
            onNoteClick = {},
            onNoteLongPress = {}
        )
    }
}