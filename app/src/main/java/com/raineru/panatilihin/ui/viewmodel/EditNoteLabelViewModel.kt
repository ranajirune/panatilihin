package com.raineru.panatilihin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.data.LabelRepository
import com.raineru.panatilihin.data.NoteAndLabelCrossRef
import com.raineru.panatilihin.data.NoteRepository
import com.raineru.panatilihin.data.defaultLabels
import com.raineru.panatilihin.data.defaultNoteAndLabelCrossRef
import com.raineru.panatilihin.data.defaultNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteLabelViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository
) : ViewModel() {

    val allLabels: StateFlow<List<Label>> = labelRepository
        .getAllLabels()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val noteId = MutableStateFlow<Long>(0)

    // TODO familiarize using a state to represent the loading of the flow
    // ref https://gist.github.com/LloydBlv/5e4b4381b6a08c3ef2c8d2f258bf96ee#file-searchviewmodel-kt
    // check sealed class SearchUiState
    @OptIn(ExperimentalCoroutinesApi::class)
    val noteLabels: StateFlow<List<Label>> = noteId
        .filter { it > 0 }
        .map(noteRepository::getNoteLabels)
        .flatMapLatest { it.filterNotNull() }
        .map { it.labels }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        insertDefaultNotes()
        insertDefaultLabels()
        insertDefaultNoteAndLabelCrossRef()
    }

    private fun insertDefaultNotes() {
        viewModelScope.launch {
            noteRepository.insertNotes(
                defaultNotes
            )
        }
    }

    private fun insertDefaultLabels() {
        viewModelScope.launch {
            labelRepository.insertLabels(
                defaultLabels
            )
        }
    }

    private fun insertDefaultNoteAndLabelCrossRef() {
        viewModelScope.launch {
            noteRepository.insertNoteLabel(
                defaultNoteAndLabelCrossRef
            )
        }
    }

    fun updateNoteId(id: Long) {
        if (noteId.value == 0L) {
            noteId.update { id }
            Log.d("EditNoteLabelViewModel", "updateNoteId: $id")
        }
    }

    fun updateNoteLabel(labelId: Long, checked: Boolean) {
        viewModelScope.launch {
            val noteAndLabelCrossRef = NoteAndLabelCrossRef(
                noteId = noteId.value,
                labelId = labelId
            )

            if (checked) {
                noteRepository.insertNoteLabel(noteAndLabelCrossRef)
            } else {
                noteRepository.deleteNoteLabel(noteAndLabelCrossRef)
            }
        }
    }

    fun createLabel(name: String) {
        viewModelScope.launch {
            noteRepository.insertLabelWithRef(
                Label(name = name), noteId.value
            )
        }
    }
}