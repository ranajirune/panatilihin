package com.raineru.panatilihin

import com.raineru.panatilihin.data.Note
import com.raineru.panatilihin.ui.EditNoteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class EditNoteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun createViewModel_returnsInitialValues() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = EditNoteViewModel(repository)

        assertEquals("", viewModel.title.value)
        assertEquals("", viewModel.content.value)
        assertEquals(false, viewModel.contentLoaded.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun settingValidNoteIdAgain_DoesNothing() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = EditNoteViewModel(repository)

        repository.insertNote(Note("title 1", "content 1"))
        repository.insertNote(Note("title 2", "content 2"))
        repository.insertNote(Note("title 3", "content 3"))

        viewModel.setNoteId(1)

        assertEquals("title 1", viewModel.title.value)
        assertEquals("content 1", viewModel.content.value)
        assertEquals(true, viewModel.contentLoaded.value)

        viewModel.setNoteId(2)

        viewModel.updateTitle("updated title 2")
        viewModel.updateContent("updated content 2")

        advanceUntilIdle()

        assertEquals("updated title 2", viewModel.title.value)
        assertEquals("updated content 2", viewModel.content.value)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getNote(2).filterNotNull().collect { note ->
                assertEquals("title 2", note.title)
                assertEquals("content 2", note.content)
            }
        }
    }

    @Test
    fun setValidNoteId_returnsNoteFromRepositoryAndSetContentLoadedToTrue() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = EditNoteViewModel(repository)

        repository.insertNote(Note("title 1", "content 1"))
        repository.insertNote(Note("title 2", "content 2"))
        repository.insertNote(Note("title 3", "content 3"))

        viewModel.setNoteId(1)

        assertEquals("title 1", viewModel.title.value)
        assertEquals("content 1", viewModel.content.value)
        assertEquals(true, viewModel.contentLoaded.value)
    }

    @Test
    fun setInvalidNoteId_DoesNothing() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = EditNoteViewModel(repository)

        repository.insertNote(Note("title 1", "content 1"))
        repository.insertNote(Note("title 2", "content 2"))
        repository.insertNote(Note("title 3", "content 3"))

        viewModel.setNoteId(99)

        assertEquals("", viewModel.title.value)
        assertEquals("", viewModel.content.value)
        assertEquals(false, viewModel.contentLoaded.value)

        viewModel.updateTitle("Updated Title 1")
        viewModel.updateContent("Updated Content 1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun setValidNoteId_UpdatesNoteInRepository() = runTest {
        val repository = FakeNoteRepository()
        val viewModel = EditNoteViewModel(repository)

        repository.insertNote(Note("title 1", "content 1"))
        repository.insertNote(Note("title 2", "content 2"))
        repository.insertNote(Note("title 3", "content 3"))

        viewModel.setNoteId(1)

        assertEquals("title 1", viewModel.title.value)
        assertEquals("content 1", viewModel.content.value)
        assertEquals(true, viewModel.contentLoaded.value)

        viewModel.updateTitle("Updated Title 1")
        viewModel.updateContent("Updated Content 1")

        advanceUntilIdle()

        backgroundScope.launch(UnconfinedTestDispatcher((testScheduler))) {
            repository.getNote(1).filterNotNull().collect { note ->
                assertEquals("Updated Title 1", note.title)
                assertEquals("Updated Content 1", note.content)
            }
        }
    }
}