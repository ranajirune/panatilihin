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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set
    private val titleFlow = snapshotFlow { title }

    var content by mutableStateOf("")
        private set
    private val contentFlow = snapshotFlow { content }

    private var noteId: Long = 0

    init {
        viewModelScope.launch {
            titleFlow
                .combine(contentFlow) { title, content ->
                    Note(id = noteId, title = title, content = content)
                }
                .filter {
                    // Note not written in disk, accept only valid input
                    // When note is written in disk, any input is valid
                    (isInputValid() && !isNoteWrittenInDisk()) ||
                            isNoteWrittenInDisk()
                }
                .debounce(500)
                .collectLatest {
                    if (!isNoteWrittenInDisk()) {
                        val id = repository.insertNote(it)
                        noteId = id
                    } else {
                        repository.updateNote(it)
                    }
                }
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    private fun isNoteWrittenInDisk(): Boolean {
        return noteId != 0L
    }

    private fun isInputValid(): Boolean {
        return (title.isBlank() && content.isNotBlank()) ||
                (title.isNotBlank() && content.isBlank()) ||
                (title.isNotBlank() && content.isNotBlank())
    }
}