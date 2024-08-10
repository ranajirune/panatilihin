package com.raineru.panatilihin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raineru.panatilihin.R
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin.ui.viewmodel.EditNoteViewModel

// TODO pin note on top app bar
// TODO redo undo feature
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteScreenContent(
    title: String,
    content: String,
    labels: List<Label>,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    contentLoaded: Boolean,
    onLabelClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier
//            .padding(horizontal = 8.dp)
            .imePadding()
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding(),
            bottom = 40.dp
        )
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
                enabled = contentLoaded,
                onTextLayout = {
                }
            )
        }
        item {
            FlowRow(
                modifier = Modifier
                    .padding(top = 38.dp)
                    .background(Color.Yellow),
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
        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
            )
        }
    }
}

// TODO content should not be covered by the keyboard upon entering details. Content should scroll up
@Composable
fun EditNoteScreen(
    noteId: Long,
    onNavigateToEditNoteLabels: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val editNoteViewModel: EditNoteViewModel = hiltViewModel()
    val labels by editNoteViewModel.noteLabels.collectAsStateWithLifecycle()
    val contentLoaded by editNoteViewModel.contentLoaded.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        editNoteViewModel.updateNoteId(noteId)
    }

    NoteScreen(
        title = editNoteViewModel.title,
        onTitleChange = editNoteViewModel::updateTitle,
        content = editNoteViewModel.content,
        onContentChange = editNoteViewModel::updateContent,
        onBackClick = onBackClick,
        contentLoaded = contentLoaded,
        labels = labels,
        onNavigateToEditNoteLabels = onNavigateToEditNoteLabels,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheet(
    showModalBottomSheet: Boolean,
    onShowModalBottomSheetValueChange: (Boolean) -> Unit,
    onLabelListItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showModalBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            dragHandle = null,
            shape = RectangleShape,
            onDismissRequest = {
                onShowModalBottomSheetValueChange(false)
            },
        ) {
            ModalBottomSheetContent(
                onShowModalBottomSheetValueChange = onShowModalBottomSheetValueChange,
                onLabelListItemClick = onLabelListItemClick
            )
        }
    }
}

@Composable
fun ModalBottomSheetContent(
    onShowModalBottomSheetValueChange: (Boolean) -> Unit,
    onLabelListItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onShowModalBottomSheetValueChange(false)
                onLabelListItemClick()
            }
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(stringResource(id = R.string.labels))
    }
}

@Preview(showBackground = true)
@Composable
private fun ModalBottomSheetContentPreview() {
    PanatilihinTheme {
        var showModalBottomSheet by rememberSaveable {
            mutableStateOf(true)
        }

        ModalBottomSheetContent(
            onShowModalBottomSheetValueChange = { showModalBottomSheet = it },
            onLabelListItemClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    onBackClick: () -> Unit,
    contentLoaded: Boolean,
    labels: List<Label>,
    onNavigateToEditNoteLabels: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showModalBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        showModalBottomSheet = showModalBottomSheet,
        onShowModalBottomSheetValueChange = { showModalBottomSheet = it },
        onLabelListItemClick = onNavigateToEditNoteLabels
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            NoteScreenContent(
                title = title,
                content = content,
                onTitleChange = onTitleChange,
                onContentChange = onContentChange,
                contentLoaded = contentLoaded,
                labels = labels,
                onLabelClick = onNavigateToEditNoteLabels,
                contentPadding = it
            )

            BottomBar(
                showModalBottomSheet = { showModalBottomSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
            )
        }
    }
}

@Composable
private fun BottomBar(
    showModalBottomSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier.background(Color.Gray)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .clickable { }
                .fillMaxWidth()
                .height(40.dp)
        ) {
            IconButton(
                onClick = {
                    showModalBottomSheet()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenContentPreview() {
    PanatilihinTheme {
        var title by rememberSaveable {
            mutableStateOf("")
        }
        var content by rememberSaveable {
            mutableStateOf("")
        }

        NoteScreen(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onContentChange = { content = it },
            onBackClick = { },
            contentLoaded = true,
            labels = listOf(
                Label("Label 1"),
                Label("Label 2"),
                Label("Label 3"),
                Label("Label 4"),
                Label("Label 5"),
            ),
            onNavigateToEditNoteLabels = {},
        )
    }
}