package com.raineru.panatilihin2.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin2.data.Note

@Composable
fun NoteEntry(
    note: Note,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                border = BorderStroke(
                    1.dp,
                    SolidColor(Color(0xFFC0C8CD))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(18.dp)
    ) {
        Text(
            text = note.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = note.content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteEntryPreview() {
    PanatilihinTheme {
        NoteEntry(
            note = Note(
                title = stringResource(id = R.string.title_initial_text_large),
                content = stringResource(id = R.string.title_initial_text_large)
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun NoteList(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = dimensionResource(id = R.dimen.note_list_vertical_margin)
                    + topPadding,
            bottom = dimensionResource(id = R.dimen.note_list_vertical_margin),
            start = dimensionResource(id = R.dimen.note_list_horizontal_margin),
            end = dimensionResource(id = R.dimen.note_list_horizontal_margin)
        ),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(notes) {
            NoteEntry(note = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListPreview() {
    val notes = listOf(
        Note(
            title = stringResource(id = R.string.title_initial_text_large),
            content = stringResource(id = R.string.title_initial_text_large)
        ),
        Note(
            title = "Lorem ipsum dolor sit amet",
            content = "Lorem ipsum dolor sit amet"
        ),
        Note(
            title = "Lorem ipsum dolor sit amet",
            content = "Lorem ipsum dolor sit amet"
        ),
        Note(
            title = "Lorem ipsum dolor sit amet",
            content = "Lorem ipsum dolor sit amet"
        ),
        Note(
            title = "Lorem ipsum dolor sit amet",
            content = "Lorem ipsum dolor sit amet"
        ),
        Note(
            title = "Lorem ipsum dolor sit amet",
            content = "Lorem ipsum dolor sit amet"
        )
    )
    PanatilihinTheme {
        NoteList(notes = notes)
    }
}