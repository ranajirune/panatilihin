package com.raineru.panatilihin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin.ui.viewmodel.CreateNoteViewModel
import com.raineru.panatilihin.ui.viewmodel.EditNoteViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditNoteForm(
    title: String,
    content: String,
    labels: List<Label>,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    contentLoaded: Boolean,
    onLabelClick: () -> Unit,
    modifier: Modifier = Modifier
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
        item {
            FlowRow(
                modifier = Modifier.padding(top = 38.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                labels.forEach { label ->
                    FilterChip(
                        selected = true,
                        onClick = onLabelClick,
                        label = {
                            Text(label.name)
                        }
                    )

                }
            }
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
                contentLoaded = true,
                labels = emptyList(),
                onLabelClick = {}
            )
        }
    }
}

@Composable
fun EditNoteScreen(
    noteId: Long,
    onLabelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val editNoteViewModel: EditNoteViewModel = hiltViewModel()

    val labels by editNoteViewModel.noteLabels.collectAsStateWithLifecycle()

    val contentLoaded by editNoteViewModel.contentLoaded.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        editNoteViewModel.updateNoteId(noteId)
    }

    EditNoteForm(
        title = editNoteViewModel.title,
        content = editNoteViewModel.content,
        onTitleChange = editNoteViewModel::updateTitle,
        onContentChange = editNoteViewModel::updateContent,
        contentLoaded = contentLoaded,
        labels = labels,
        onLabelClick = onLabelClick,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun EditNoteScreenPreview() {
    PanatilihinTheme {
        var title by remember {
            mutableStateOf("This is the title dont ask")
        }
        var content by remember {
            mutableStateOf("And I'm the content now...")
        }

        val labels = listOf(
            Label(name = "Label 1", id = 1L),
            Label(name = "Label 2", id = 2L),
            Label(name = "Label 3", id = 3L),
            Label(name = "Label 4", id = 4L),
            Label(name = "Label 5", id = 5L),
            Label(name = "Label 6", id = 6L),
            Label(name = "Label 7", id = 7L),
            Label(name = "Label 8", id = 8L),
        )

        EditNoteForm(
            title = title,
            content = content,
            labels = labels,
            onTitleChange = { title = it },
            onContentChange = { content = it },
            contentLoaded = true,
            onLabelClick = {}
        )
    }
}