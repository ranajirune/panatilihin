package com.raineru.panatilihin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
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

    // Prevent updating title and content if content not yet loaded
    private val _contentLoaded = MutableStateFlow(false)
    val contentLoaded: StateFlow<Boolean> = _contentLoaded.asStateFlow()

    private val noteId: MutableStateFlow<Long> = MutableStateFlow(0)

    fun updateTitle(title: String) {
        if (contentLoaded.value) {
            this.title = title
        }
    }

    fun updateContent(content: String) {
        if (contentLoaded.value) {
            this.content = content
        }
    }

    fun setNoteId(noteId: Long) {
        if (!contentLoaded.value) {
            this.noteId.value = noteId
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
            noteId.filter { it != 0L }
                .take(1)
                .collectLatest { noteId ->
                    repository.getNote(noteId)
                        .filterNotNull()
                        .take(1)
                        .collectLatest { fetchedNote ->
                            println("fetchedNote: $fetchedNote")
                            title = fetchedNote.title
                            content = fetchedNote.content
                            _contentLoaded.update {
                                println("_contentLoaded: true")
                                true
                            }
                        }
                }
        }

        // Observe changes to title and content and update note in repo
        viewModelScope.launch {
            // If contentLoaded is true, noteId is already set
            contentLoaded
                // Filter if content not yet loaded
                .filter { it }
                .combine(noteId) { _, noteId ->
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

