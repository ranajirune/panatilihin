package com.raineru.panatilihin.ui

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private var noteId: Long = 0

    init {
        viewModelScope.launch {
            _title
                .combine(_content) { title, content ->
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
        _title.value = title
    }

    fun updateContent(content: String) {
        _content.value = content
    }

    private fun isNoteWrittenInDisk(): Boolean {
        return noteId != 0L
    }

    private fun isInputValid(): Boolean {
        return (title.value.isBlank() && content.value.isNotBlank()) ||
                (title.value.isNotBlank() && content.value.isBlank()) ||
                (title.value.isNotBlank() && content.value.isNotBlank())
    }
}