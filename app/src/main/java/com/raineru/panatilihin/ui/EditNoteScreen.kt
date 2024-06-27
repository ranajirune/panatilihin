package com.raineru.panatilihin.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditNoteForm(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    contentLoaded: Boolean
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            BasicTextField(
                value = title,
                enabled = contentLoaded,
                onValueChange = onTitleChange,
                decorationBox = {
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (title.isEmpty()) {
                            Text(
                                "Title",
                                style = TextStyle.Default.copy(fontSize = 24.sp),
                                color = Color(0xFFAAA4A2)
                            )
                        }
                        it()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            BasicTextField(
                value = content,
                onValueChange = onContentChange,
                decorationBox = {
                    Box(contentAlignment = Alignment.TopStart) {
                        if (content.isEmpty()) {
                            Text(
                                "Note",
                                style = TextStyle.Default.copy(fontSize = 16.sp),
                                color = Color(0xFFAAA4A2)
                            )
                        }
                        it()
                    }
                },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                enabled = contentLoaded
            )
        }
    }
}

@Composable
fun CreateNoteScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues()
) {
    val createNoteViewModel: CreateNoteViewModel = hiltViewModel()

    Column(
        modifier = modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            EditNoteForm(
                title = createNoteViewModel.title,
                content = createNoteViewModel.content,
                onTitleChange = createNoteViewModel::updateTitle,
                onContentChange = createNoteViewModel::updateContent,
                contentLoaded = true
            )
        }
    }
}

@Composable
fun EditNoteScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    padding: PaddingValues = PaddingValues()
) {
    val editNoteViewModel: EditNoteViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        editNoteViewModel.updateNoteId(noteId)
    }

    Column(
        modifier = modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        EditNoteForm(
            title = editNoteViewModel.title,
            content = editNoteViewModel.content,
            onTitleChange = editNoteViewModel::updateTitle,
            onContentChange = editNoteViewModel::updateContent,
            contentLoaded = editNoteViewModel.contentLoaded
        )
    }
}