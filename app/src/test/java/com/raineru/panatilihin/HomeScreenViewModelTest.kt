package com.raineru.panatilihin

import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.data.NotesRepository
import com.raineru.panatilihin.ui.HomeScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

private val defaultDataContents = listOf(
    Note("Nobody - from Kaiju No. 8", "OneRepublic", 1),
    Note("Bling-Bang-Bang-Born", "Creepy Nuts", 2),
    Note("Abyss - from Kaiju No. 8", "YUNGBLUD", 3)
)

class HomeScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeScreenViewModel_givenDefaultData_returnsDefaultData() = runTest {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.notes.collect()
        }

        assertEquals(defaultDataContents, viewModel.notes.value)
    }

    @Test
    fun noteLongPressed_togglesSelectedNote() = runTest {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        assertEquals(0, viewModel.selectedNotes.size)

        viewModel.noteLongPressed(1)

        assertEquals(1, viewModel.selectedNotes.size)

        viewModel.noteLongPressed(1)

        assertEquals(0, viewModel.selectedNotes.size)
    }

    @Test
    fun cancelSelectionMode_clearsSelectedNotes() = runTest {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        viewModel.noteLongPressed(1)
        viewModel.cancelSelectionMode()
        assertEquals(0, viewModel.selectedNotes.size)
    }

    @Test
    fun isInSelectionMode_givenSelectedNotes_returnsTrue() = runTest {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        assertEquals(false, viewModel.isInSelectionMode())

        viewModel.noteLongPressed(3)
        assertEquals(true, viewModel.isInSelectionMode())

        viewModel.noteLongPressed(3)
        assertEquals(false, viewModel.isInSelectionMode())
    }

    @Test
    fun selectedNotesCount_givenSelectedNotes_returnsCount() = runTest {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        assertEquals(0, viewModel.selectedNotesCount())

        viewModel.noteLongPressed(1)
        viewModel.noteLongPressed(3)

        assertEquals(2, viewModel.selectedNotesCount())
    }

    @Test
    fun noteClicked_whileNotInSelectionMode_doesNothing() {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        assertEquals(0, viewModel.selectedNotes.size)

        viewModel.noteClicked(3)
        assertEquals(0, viewModel.selectedNotes.size)
    }

    @Test
    fun noteClicked_whileInSelectionMode_functionsSameAsNoteLongPressed() {
        val repository: NotesRepository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        assertEquals(0, viewModel.selectedNotes.size)

        viewModel.noteLongPressed(2)
        viewModel.noteClicked(3)

        assertEquals(2, viewModel.selectedNotes.size)

        viewModel.noteClicked(3)
        assertEquals(1, viewModel.selectedNotes.size)

        viewModel.noteClicked(2)
        assertEquals(0, viewModel.selectedNotes.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteSelectedNotes_deletesSelectedNotes() = runTest {
        val repository = FakeNotesRepository()
        val viewModel = HomeScreenViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.notes.collect()
        }

        viewModel.noteLongPressed(1)
        viewModel.noteLongPressed(2)
        viewModel.deleteSelectedNotes()

        assertEquals(1, viewModel.notes.value.size)
        assertEquals(0, viewModel.selectedNotes.size)
    }
}

class FakeNotesRepository : NotesRepository {
    private val _data: MutableList<Note> = mutableListOf(
        Note("Nobody - from Kaiju No. 8", "OneRepublic", 1),
        Note("Bling-Bang-Bang-Born", "Creepy Nuts", 2),
        Note("Abyss - from Kaiju No. 8", "YUNGBLUD", 3)
    )

    private val _dataFlow: MutableStateFlow<List<Note>> = MutableStateFlow(_data)
    val dataFlow = _dataFlow.asStateFlow()

    override fun getAllNotes(): Flow<List<Note>> = dataFlow

    override fun getNote(id: Long): Flow<Note?> {
        return flow {
            emit(_data.find { it.id == id })
        }
    }

    override suspend fun insertNote(item: Note): Long {
        _data.add(item)
        _dataFlow.value = _data
        return item.id
    }

    override suspend fun deleteNote(item: Note) {
        _data.remove(item)
        _dataFlow.value = _data
        println(_dataFlow.value)
    }

    override suspend fun deleteNote(id: Long) {
        _data.removeIf { it.id == id }
        _dataFlow.value = _data
    }

    override suspend fun updateNote(item: Note) {
        val i = _data.indexOfFirst { it.id == item.id }

        if (i >= 0) {
            _data[i] = item
            _dataFlow.value = _data
        }
    }
}