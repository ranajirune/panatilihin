package com.raineru.panatilihin2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin2.data.Note
import com.raineru.panatilihin2.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    noteRepository: NoteRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = noteRepository.getAllNotes()
        .stateIn(
            viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000)
        )
}
