package com.raineru.panatilihin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val showDropdownMenu: MutableState<Boolean> = mutableStateOf(false)

    val notes = repository.getAllNotes().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedNotes = mutableStateListOf<Long>()
    val selectedNotes: List<Long> = _selectedNotes

    private fun toggleNodeSelection(noteId: Long) {
        if (_selectedNotes.contains(noteId)) {
            _selectedNotes.remove(noteId)
        } else {
            _selectedNotes.add(noteId)
        }
    }

    fun cancelSelectionMode() {
        _selectedNotes.clear()
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
            _selectedNotes.forEach {
                withContext(Dispatchers.IO) {
                    repository.deleteNote(it)
                }
            }
            _selectedNotes.clear()
        }
    }

    fun isInSelectionMode(): Boolean {
        return _selectedNotes.isNotEmpty()
    }

    fun selectedNotesCount(): Int {
        return _selectedNotes.size
    }
}