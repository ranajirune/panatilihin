package com.raineru.panatilihin.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var title: String by mutableStateOf("")
        private set
    private val titleFlow = snapshotFlow { title }

    var content: String by mutableStateOf("")
        private set
    private val contentFlow = snapshotFlow { content }

    private val _contentLoaded = MutableStateFlow(false)
    val contentLoaded = _contentLoaded.asStateFlow()

    private val noteId = MutableStateFlow(0L)

    private val _noteLabels = MutableStateFlow<List<Label>>(emptyList())
    val noteLabels = _noteLabels.asStateFlow()

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

    fun updateNoteId(id: Long) {
        if (noteId.value == 0L) {
            noteId.update { id }

            viewModelScope.launch {
                noteRepository.getNoteLabels(noteId.value)
                    .filterNotNull()
                    .take(1)
                    .collect {
                        title = it.note.title
                        content = it.note.content
                        _noteLabels.value = it.labels
                        _contentLoaded.update { true }
                    }

                combine(
                    flow = noteId,
                    flow2 = titleFlow,
                    flow3 = contentFlow
                ) { id, title, content ->
                    Note(id = id, title = title, content = content)
                }
                    .debounce(500)
                    .collect {
                        noteRepository.updateNote(it)
                    }
            }
        }
    }
}

