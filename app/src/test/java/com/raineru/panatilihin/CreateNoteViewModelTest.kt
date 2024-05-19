package com.raineru.panatilihin

import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NotesRepository
import com.raineru.panatilihin.ui.CreateNoteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CreateNoteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun givenInvalidTitleAndContent_dontWriteNoteInDisk() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = CreateNoteViewModel(repository)

        val notes = repository.getAllNotes().stateIn(
            scope = backgroundScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        var title = "        "
        var content = "        "
        viewModel.updateTitle(title)
        viewModel.updateContent(content)
        assertEquals(listOf(), notes.value)

        title = "\t\r\n"
        content = "\t\r\n"
        viewModel.updateTitle(title)
        viewModel.updateContent(content)
        assertEquals(listOf(), notes.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenValidTitleAndContent_writeNoteInDisk() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = CreateNoteViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getAllNotes().collect()
        }

        val title = "Nobody - from Kaiju No. 8"
        val content = "OneRepublic"
        viewModel.updateTitle(title)
        viewModel.updateContent(content)

        advanceUntilIdle()

        assertEquals(
            listOf(Note(title, content, 1)),
            repository.getAllNotes().value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenValidTitleAndContent_updatingTitleAndContent_UpdatesNoteInDisk() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = CreateNoteViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getAllNotes().collect()
        }

        var title = "Nobody - from Kaiju No. 8"
        var content = "OneRepublic"
        viewModel.updateTitle(title)
        viewModel.updateContent(content)

        advanceUntilIdle()

        assertEquals(
            listOf(Note(title, content, 1)),
            repository.getAllNotes().value
        )

        title = "Bling-Bang-Bang-Born"
        content = "Creepy Nuts"
        viewModel.updateTitle(title)
        viewModel.updateContent(content)

        advanceUntilIdle()

        assertEquals(
            listOf(Note(title, content, 1)),
            repository.getAllNotes().value
        )
    }
}

class FakeNoteRepository : NotesRepository {

    private val notes = mutableListOf<Note>()

    private val _notesFlow = MutableStateFlow<List<Note>>(notes)

    override fun getAllNotes(): StateFlow<List<Note>> {
        return _notesFlow
    }

    override fun getNote(id: Long): Flow<Note?> {
        return flow {
            emit(notes.find { it.id == id })
        }
    }

    override suspend fun insertNote(item: Note): Long {
        println("insertNote: $item")
        notes.add(item.copy(id = notes.size.toLong() + 1))
        _notesFlow.value = notes.toList()
        return notes.size.toLong()
    }

    override suspend fun deleteNote(item: Note) {
        notes.remove(item)
    }

    override suspend fun deleteNote(id: Long) {
        val i = notes.indexOfFirst { it.id == id }
        notes.removeAt(i)
    }

    override suspend fun updateNote(item: Note) {
        println("updateNote: $item")
        val i = notes.indexOfFirst { it.id == item.id }
        notes[i] = item
        _notesFlow.value = notes.toList()
    }
}