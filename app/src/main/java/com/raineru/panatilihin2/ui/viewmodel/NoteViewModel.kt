@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.raineru.panatilihin2.ui.viewmodel

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin2.data.Note
import com.raineru.panatilihin2.data.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    private val noteRepository: NoteRepository,
    @Assisted("id") private var id: Long,
    @Assisted("title") private val title: String,
    @Assisted("content") private val content: String
) : ViewModel() {

    val noteTitleState = TextFieldState(initialText = title)
    val noteContentState = TextFieldState(initialText = content)

    private val noteFlow: StateFlow<Note> = snapshotFlow { noteTitleState.text }
        .combine(snapshotFlow { noteContentState.text }) { title, content ->
            Log.d("NoteViewModel", "combine: $title, $content")
            Note(
                title = title.toString(),
                content = content.toString(),
                id = id
            )
        }
        .debounce(500)
        .filter {
            it.title.isNotEmpty() || it.content.isNotEmpty()
        }
        .mapLatest {
            if (id == 0L) {
                id = noteRepository.insertNote(it)
                it.copy(id = id)
            } else {
                noteRepository.updateNote(it)
                it
            }
        }
        .stateIn(
            viewModelScope,
            initialValue = Note(
                title = title,
                content = content,
                id = id
            ),
            started = SharingStarted.WhileSubscribed(5000)
        )

    init {
        // Just collect the note flow to save the changes to the database
        viewModelScope.launch {
            noteFlow.collectLatest {}
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("id") id: Long,
            @Assisted("title") title: String,
            @Assisted("content") content: String
        ): NoteViewModel
    }
}