package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin.ui.viewmodel.EditNoteLabelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteLabelScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    allLabels: List<Label>,
    selectedLabels: List<Long>,
    onUpdateLabel: (Long, Boolean) -> Unit,
    onCreateNewLabel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    LabelSearchBar(
                        text = query,
                        onTextChange = onQueryChange,
                        onBackClick = onBackClick
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        EditNoteLabelList(
            allLabels = allLabels,
            selectedLabels = selectedLabels,
            query = query,
            onUpdateLabel = onUpdateLabel,
            onUpdateQuery = onQueryChange,
            onCreateNewLabel = onCreateNewLabel,
            contentPadding = padding,
            modifier = modifier
        )
    }
}

@Composable
fun EditNoteLabelScreen(
    noteId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    editNoteLabelViewModel: EditNoteLabelViewModel = hiltViewModel()
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    val allLabels by editNoteLabelViewModel.allLabels.collectAsStateWithLifecycle()
    val noteLabels by editNoteLabelViewModel.noteLabels.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        editNoteLabelViewModel.updateNoteId(noteId)
    }

    EditNoteLabelScreen(
        allLabels = allLabels,
        onUpdateLabel = { id: Long, checked: Boolean ->
            editNoteLabelViewModel.updateNoteLabel(id, checked)
        },
        query = query,
        onCreateNewLabel = {
            editNoteLabelViewModel.createLabel(name = it)
        },
        selectedLabels = noteLabels.map { it.id },
        modifier = modifier,
        onBackClick = onBackClick,
        onQueryChange = {
            query = it
        }
    )
}

@Composable
fun EditNoteLabelList(
    allLabels: List<Label>,
    selectedLabels: List<Long>,
    query: String,
    onUpdateLabel: (Long, Boolean) -> Unit,
    onUpdateQuery: (String) -> Unit,
    onCreateNewLabel: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding()
        )
    ) {
        if (
            allLabels.count {
                it.name.contentEquals(
                    query.trim(),
                    ignoreCase = true
                )
            } == 0 &&
            query.isNotBlank()
        ) {
            item(key = 0) {
                CreateNewLabel(
                    text = query.trim(),
                    onClick = {
                        onCreateNewLabel(it)
                        onUpdateQuery("")
                    }
                )
            }
        }

        allLabels
            .filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            .forEach { label ->
                item(key = label.id) {
                    ToggleableLabel(
                        checked = selectedLabels.contains(label.id),
                        text = label.name,
                        onCheckedChange = {
                            onUpdateLabel(label.id, it)
                        }
                    )
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

@Composable
private fun ToggleableLabel(
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    ToggleableLabelContent(
        text = text,
        onCheckedChange = onCheckedChange,
        checked = checked,
        modifier = modifier
    )
}

@Composable
private fun ToggleableLabelContent(
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onCheckedChange.invoke(!checked)
            }
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.MailOutline, contentDescription = null)
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange.invoke(it)
            },
        )
    }
}

@Composable
private fun ToggleableLabel(
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }

    ToggleableLabelContent(
        text = text,
        onCheckedChange = {
            checked = it
            onCheckedChange(it)
        },
        checked = checked,
        modifier = modifier
    )
}

@Composable
private fun CreateNewLabel(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onClick(text)
            }
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = "Create \"$text\"",
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ToggleableLabelPreview() {
    PanatilihinTheme {
        val text by rememberSaveable {
            mutableStateOf("A New Label")
        }
        ToggleableLabel(
            text = text,
            onCheckedChange = {

            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditNoteLabelScreenPreview() {
    PanatilihinTheme {
        val selectedLabels = remember {
            mutableStateListOf(
                1L, 3L, 5L
            )
        }

        val allLabels = listOf(
            Label(name = "Label 1", id = 1L),
            Label(name = "Label 2", id = 2L),
            Label(name = "Label 3", id = 3L),
            Label(name = "Label 4", id = 4L),
            Label(name = "Label 5", id = 5L),
        )

        var query by remember {
            mutableStateOf("")
        }

        EditNoteLabelList(
            allLabels = allLabels,
            onUpdateLabel = { id, checked ->
                if (checked) {
                    selectedLabels.add(id)
                } else {
                    selectedLabels.remove(id)
                }
            },
            query = query,
            onUpdateQuery = {
                query = it
            },
            onCreateNewLabel = {},
            selectedLabels = selectedLabels,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateNewLabelPreview() {
    PanatilihinTheme {
        CreateNewLabel(text = "Label S", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HoistedToggleableLabelPreview() {
    PanatilihinTheme {
        val text = "Bulbasaur"
        var checked by remember {
            mutableStateOf(false)
        }
        ToggleableLabel(
            text = text,
            onCheckedChange = {
                checked = it
                Log.d("EditNoteLabelScreen", "onCheckedChange: $it")
            },
            checked = checked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotHoistedToggleableLabelPreview() {
    PanatilihinTheme {
        val text = "Bulbasaur"
        ToggleableLabel(
            text = text,
            onCheckedChange = {
                Log.d("EditNoteLabelScreen", "onCheckedChange: $it")
            }
        )
    }
}

@Composable
private fun LabelSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(12.dp))
        BasicTextField(
            value = text,
            textStyle = MaterialTheme.typography.labelLarge,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            decorationBox = {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            "Enter label name",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    it()
                }
            }
        )
    }
}