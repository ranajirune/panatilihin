package com.raineru.panatilihin2.ui.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.raineru.panatilihin2.data.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    private val noteRepository: NoteRepository,
    @Assisted(value = "title") private val title: String,
    @Assisted(value = "content") private val content: String
) : ViewModel() {

    val noteTitleState = TextFieldState(initialText = title)
    val noteContentState = TextFieldState(initialText = content)

    /*fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }*/

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("title") title: String,
            @Assisted("content") content: String
        ): NoteViewModel
    }
}