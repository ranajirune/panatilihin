package com.raineru.panatilihin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raineru.panatilihin.R
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NoteLabels
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin.ui.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val LABEL_COUNT_TO_SHOW_AFTER_COLLAPSING = 2
private const val MAX_LABEL_TO_SHOW = 3

// TODO: Top app bar padding. Scroll top app bar along with list scrolling
// TODO put pinned and newer notes on top
@Composable
fun HomeScreen(
    notes: List<NoteLabels>,
    selectedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    onMenuClick: () -> Unit,
    onMenuLabelClick: (Long) -> Unit,
    labels: List<Label>,
    onCreateNewLabelClick: () -> Unit,
    onEditLabelClick: () -> Unit,
    drawerState: DrawerState,
    hasEmptyNote: Boolean,
    onEmptyNoteNotificationComplete: () -> Unit,
    onCreateNewNoteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        if (hasEmptyNote) {
            delay(1000)
            snackbarHostState.showSnackbar(
                "Empty note discarded"
            )
            onEmptyNoteNotificationComplete()
        }
    }

    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(onClick = { onCreateNewNoteClick() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        HomeScreenContent(
            notes = notes,
            selectedNotes = selectedNotes,
            onNoteLongPress = onNoteLongPress,
            onNoteClick = onNoteClick,
            modifier = modifier.padding(it),
            onMenuClick = onMenuClick,
            onMenuLabelClick = onMenuLabelClick,
            labels = labels,
            onCreateNewLabelClick = onCreateNewLabelClick,
            onEditLabelClick = onEditLabelClick,
            drawerState = drawerState
        )
    }
}

@Composable
fun HomeScreen(
    onNoteClick: (Long) -> Unit,
    onCreateNewNoteClick: () -> Unit,
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val coroutineScope = rememberCoroutineScope()

    val noteLabels by homeScreenViewModel.notes.collectAsStateWithLifecycle()
    val labels by homeScreenViewModel.labels.collectAsStateWithLifecycle()
    val hasEmptyNote by homeScreenViewModel.hasEmptyNote.collectAsStateWithLifecycle()
    val draftNote by homeScreenViewModel.draftNote.collectAsStateWithLifecycle()

    Column {
        Text("hasEmptyNote: $hasEmptyNote")
        Text("draftNote: $draftNote")
        HomeScreen(
            modifier = modifier,
            labels = labels,
            drawerState = drawerState,
            selectedNotes = homeScreenViewModel.selectedNotes,
            notes = noteLabels,
            onNoteClick = {
                if (!homeScreenViewModel.isInSelectionMode()) {
                    onNoteClick(it)
                } else {
                    homeScreenViewModel.noteClicked(it)
                }
            },
            onMenuLabelClick = {

            },
            onMenuClick = {
                coroutineScope.launch {
                    drawerState.open()
                }
            },
            onCreateNewLabelClick = {

            },
            onEditLabelClick = {

            },
            hasEmptyNote = hasEmptyNote,
            onNoteLongPress = homeScreenViewModel::noteLongPressed,
            onEmptyNoteNotificationComplete = { homeScreenViewModel.deleteDraftNote() },
            onCreateNewNoteClick = onCreateNewNoteClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    notes: List<NoteLabels>,
    selectedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    onMenuClick: () -> Unit,
    labels: List<Label>,
    drawerState: DrawerState,
    onCreateNewLabelClick: () -> Unit,
    onEditLabelClick: () -> Unit,
    onMenuLabelClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    PanatilihinNavigationDrawer(
        labels = labels,
        drawerState = drawerState,
        onCreateNewLabelClick = onCreateNewLabelClick,
        onEditLabelClick = onEditLabelClick,
        onMenuLabelClick = onMenuLabelClick
    ) {
        val appBarMaxHeightPx = with(LocalDensity.current) {
            val barTopPadding = 8.dp
            (SearchBarDefaults.InputFieldHeight + barTopPadding)
                .roundToPx()
        }
        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(connection)
        ) {
            NoteList(
                notes = notes,
                selectedNotes = selectedNotes,
                onNoteClick = onNoteClick,
                onNoteLongPress = onNoteLongPress,
                topPadding = SearchBarDefaults.InputFieldHeight
                        + dimensionResource(id = R.dimen.note_list_vertical_margin)
            )

            HomeScreenAppBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { query = it },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onMenuClick = onMenuClick,
                yOffset = connection.appBarOffset,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
//                Text("I'm the note list")
            }
        }
    }
}

@Composable
fun NoteList(
    notes: List<NoteLabels>,
    selectedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    topPadding: Dp,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        // Usually from scaffold's top bar height and bottom bar height
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
            NoteEntry(
                note = it.note,
                isSelected = it.note.id in selectedNotes,
                onNoteClick = onNoteClick,
                onNoteLongPress = onNoteLongPress,
                labels = it.labels.map { label -> label.name }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NoteListPreview(
    @PreviewParameter(NotePreviewParameterProvider::class)
    notes: List<NoteLabels>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    HomeScreen(
        onEmptyNoteNotificationComplete = {},
        notes = notes,
        selectedNotes = listOf(3L, 5L, 7L),
        onNoteClick = {

        },
        onNoteLongPress = {},
        onMenuClick = {
            scope.launch {
                drawerState.open()
            }
        },
        onMenuLabelClick = {},
        labels = listOf(
            Label(name = "Label 1", id = 1L),
            Label(name = "Label 2", id = 2L),
            Label(name = "Label 3", id = 3L),
        ),
        onCreateNewLabelClick = {},
        onEditLabelClick = {},
        drawerState = drawerState,
        hasEmptyNote = false,
        onCreateNewNoteClick = {}
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun NoteEntry(
    note: Note,
    isSelected: Boolean,
    onNoteClick: (Long) -> Unit,
    onNoteLongPress: (Long) -> Unit,
    labels: List<String>,
    modifier: Modifier = Modifier,
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
        if (labels.isNotEmpty()) {
            Spacer(modifier = Modifier.height(18.dp))

            // TODO remove clickable chips, should open the note upon clicking label. not its own click event
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (labels.size <= MAX_LABEL_TO_SHOW) {
                    labels.forEach {
                        FilterChip(
                            selected = true,
                            onClick = {},
                            label = {
                                Text(it)
                            }
                        )
                    }
                } else {
                    labels.take(LABEL_COUNT_TO_SHOW_AFTER_COLLAPSING)
                        .forEach { label ->
                            FilterChip(
                                selected = true,
                                onClick = {},
                                label = {
                                    Text(label)
                                }
                            )
                        }
                    FilterChip(
                        selected = true,
                        onClick = {},
                        label = {
                            Text("+${labels.size - LABEL_COUNT_TO_SHOW_AFTER_COLLAPSING}")
                        }
                    )
                }
            }
        }
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
            onNoteLongPress = {},
            labels = emptyList()
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
            onNoteLongPress = {},
            labels = emptyList()
        )
    }
}

@Preview(
    showBackground = true,
    name = "Note with 6 labels"
)
@Composable
fun NoteWith6LabelsPreview() {
    PanatilihinTheme {
        NoteEntry(
            note = Note(
                title = "This is the title you m....",
                content = "This is the content you m...."
            ),
            isSelected = true,
            onNoteClick = {},
            onNoteLongPress = {},
            labels = listOf(
                "Label 1",
                "Label 2",
                "Label 3",
                "Label 4",
                "Label 5",
                "Label 6"
            )
        )
    }
}

@Preview(
    showBackground = true,
    name = "Note with 3 labels"
)
@Composable
fun NoteWith3LabelsPreview() {
    PanatilihinTheme {
        NoteEntry(
            note = Note(
                title = "This is the title you m....",
                content = "This is the content you m...."
            ),
            isSelected = true,
            onNoteClick = {},
            onNoteLongPress = {},
            labels = listOf(
                "Label 1",
                "Label 2",
                "Label 3"
            )
        )
    }
}

@Preview(
    showBackground = true,
    name = "Note with 4 labels"
)
@Composable
fun NoteWith4LabelsPreview() {
    PanatilihinTheme {
        NoteEntry(
            note = Note(
                title = "This is the title you m....",
                content = "This is the content you m...."
            ),
            isSelected = true,
            onNoteClick = {},
            onNoteLongPress = {},
            labels = listOf(
                "1",
                "2",
                "Label 3",
                "Label 4"
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onMenuClick: () -> Unit,
    yOffset: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    // TODO fill width as much as possible but with padding. After search bar expands to full screen, padding shouldn't be visible on the sides
    SearchBar(
        modifier = modifier.offset { IntOffset(0, yOffset) },
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = {
                    Text("Search your notes")
                },
                leadingIcon = {
                    if (expanded) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = onMenuClick) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenAppBarPreview() {
    PanatilihinTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            HomeScreenAppBar(
                query = "",
                onQueryChange = {},
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                modifier = Modifier.align(Alignment.TopCenter),
                onMenuClick = {},
                yOffset = 0
            ) {

            }
        }
    }
}