package com.raineru.panatilihin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.NotesRepository
import com.raineru.panatilihin.data.SelectableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val showDropdownMenu: MutableState<Boolean> = mutableStateOf(false)

    private val notes = repository.getAllNotes()

    val selectableNotes: SnapshotStateList<SelectableNote> = mutableStateListOf()

    private fun toggleNodeSelection(noteId: Long) {
        val note = selectableNotes.find { it.note.id == noteId }
        val index = selectableNotes.indexOf(note)

        note?.let {
            selectableNotes.set(index, note.copy(isSelected = !note.isSelected))
        }
    }

    fun cancelSelectionMode() {
        val selectedNoteIndices = selectableNotes.filter { it.isSelected }
            .map { selectableNotes.indexOf(it) }

        selectedNoteIndices.forEach {
            selectableNotes[it] = selectableNotes[it].copy(isSelected = false)
        }
    }

    fun noteClicked(noteId: Long) {
        if (isInSelectionMode()) {
            noteLongPressed(noteId)
        }
    }

    fun noteLongPressed(noteId: Long) {
        toggleNodeSelection(noteId)
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch {
            selectableNotes
                .filter { it.isSelected }
                .map { it.note }
                .forEach { repository.deleteNote(it) }
        }
    }

    fun isInSelectionMode(): Boolean {
        return selectableNotes.count { it.isSelected } > 0
    }

    fun selectedNotesCount(): Int {
        return selectableNotes.count { it.isSelected }
    }

    fun loadSelectableNotes() {
        viewModelScope.launch {
            notes.distinctUntilChanged().collectLatest {
                selectableNotes.clear()
                selectableNotes.addAll(it.map { note ->
                    SelectableNote(note, false)
                })
            }
        }
    }
}