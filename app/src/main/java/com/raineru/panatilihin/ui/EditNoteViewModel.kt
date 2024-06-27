package com.raineru.panatilihin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    var title: String by mutableStateOf("")
        private set

    private val titleFlow = snapshotFlow { title }

    var content: String by mutableStateOf("")
        private set

    private val contentFlow = snapshotFlow { content }

    var contentLoaded by mutableStateOf(false)
        private set

    private val contentLoadedFlow = snapshotFlow { contentLoaded }

    private var noteId by mutableLongStateOf(0)
    private val noteIdFlow = snapshotFlow { noteId }

    fun updateTitle(title: String) {
        if (contentLoaded) {
            this.title = title
        }
    }

    fun updateContent(content: String) {
        if (contentLoaded) {
            this.content = content
        }
    }

    fun updateNoteId(noteId: Long) {
        if (!contentLoaded) {
            this.noteId = noteId
        }
    }

    fun insertTestNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    init {
        // Update title and content from repo when noteId changes
        viewModelScope.launch {
            noteIdFlow.filter { it != 0L }
                .take(1)
                .collectLatest { noteId ->
                    repository.getNote(noteId)
                        .filterNotNull()
                        .take(1)
                        .collectLatest { fetchedNote ->
                            println("fetchedNote: $fetchedNote")
                            title = fetchedNote.title
                            content = fetchedNote.content
                            contentLoaded = true
                        }
                }
        }

        // Observe changes to title and content and update note in repo
        viewModelScope.launch {
            // If contentLoaded is true, noteId is already set
            contentLoadedFlow
                // Filter if content not yet loaded
                .filter { it }
                .combine(noteIdFlow) { _, noteId ->
                    noteId
                }
                .combine(titleFlow) { noteId, title ->
                    Note(id = noteId, title = title, content = "")
                }
                .combine(contentFlow) { note, content ->
                    note.copy(content = content)
                }
                .debounce(500)
                .collectLatest {
                    println("EditNoteViewModel: updateNote: $it")
                    repository.updateNote(it)
                }
        }
    }
}

