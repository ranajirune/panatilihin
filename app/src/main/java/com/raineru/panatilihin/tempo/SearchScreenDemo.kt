package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    repository: NotesRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    val searchResults: StateFlow<List<Note>> =
        snapshotFlow { query }
            .combine(repository.getAllNotes()) { query, notes ->
                when {
                    query.isNotEmpty() -> notes.filter { note ->
                        note.title.contains(query, ignoreCase = true) ||
                                note.content.contains(query, ignoreCase = true)
                    }

                    else -> notes
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun onQueryChange(query: String) {
        this.query = query
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
        initialValue = emptyList()
    )

    SearchScreen(
        searchQuery = viewModel.query,
        searchResults = searchResults,
        onQueryChange = viewModel::onQueryChange,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<Note>,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        modifier = modifier,
        query = searchQuery,
        onQueryChange = onQueryChange,
        onSearch = { keyboardController?.hide() },
        active = true,
        onActiveChange = {},
        placeholder = {
            Text(text = "Search character")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        tonalElevation = 0.dp
    ) {
        if (searchResults.isEmpty()) {
            NoteListEmptyState()
        } else {
            LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(82.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = searchResults.size,
                    key = { index ->
                        searchResults[index].id
                    }
                ) { index ->
                    val note = searchResults[index]
                    NoteListItem(note = note)
                }
            }
        }
    }
}

@Composable
fun NoteListItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = note.title)
//        Text(text = note.labels.toString())
        Text(text = note.content)
    }
}

@Composable
fun NoteListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No character found",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}