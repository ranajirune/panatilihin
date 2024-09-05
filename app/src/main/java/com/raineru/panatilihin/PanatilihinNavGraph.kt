package com.raineru.panatilihin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.navigation.createNoteScreenNavigationRoute
import com.raineru.panatilihin.navigation.editDraftNoteLabelScreenNavigationRoute
import com.raineru.panatilihin.navigation.editNoteLabelScreenNavigationRoute
import com.raineru.panatilihin.navigation.editNoteScreenNavigationRoute
import com.raineru.panatilihin.navigation.homeScreenNavigationRoute
import com.raineru.panatilihin.navigation.navigateToCreateNoteScreen
import com.raineru.panatilihin.navigation.navigateToEditNoteLabelScreen
import com.raineru.panatilihin.navigation.navigateToEditNoteScreen
import com.raineru.panatilihin.navigation.noteIdParameter
import com.raineru.panatilihin.ui.CreateNoteScreen
import com.raineru.panatilihin.ui.EditNoteLabelList
import com.raineru.panatilihin.ui.EditNoteLabelScreen
import com.raineru.panatilihin.ui.EditNoteScreen
import com.raineru.panatilihin.ui.HomeScreen
import com.raineru.panatilihin.ui.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanatilihinNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreenNavigationRoute,
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val allLabels by homeScreenViewModel.labels.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = homeScreenNavigationRoute
        ) {
            LaunchedEffect(Unit) {
                homeScreenViewModel.writeToDiskValidDraftNote()
            }

            HomeScreen(
                onNoteClick = { id ->
                    navController.navigateToEditNoteScreen(id)
                },
                onCreateNewNoteClick = {
                    homeScreenViewModel.draftNote()
                    navController.navigateToCreateNoteScreen()
                },
                homeScreenViewModel = homeScreenViewModel,
                onCancelNoteSelection = homeScreenViewModel::cancelSelection,
                onSelectedNotesDelete = {
                    homeScreenViewModel.deleteSelectedNotes()
                }
            )
        }

        composable(
            route = "$editNoteScreenNavigationRoute/{$noteIdParameter}",
            arguments = listOf(navArgument(noteIdParameter) { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong(noteIdParameter) ?: 0
            EditNoteScreen(
                noteId = noteId,
                onNavigateToEditNoteLabels = {
                    navController.navigateToEditNoteLabelScreen(noteId)
                },
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(
            route = "$editNoteLabelScreenNavigationRoute/{$noteIdParameter}",
            arguments = listOf(navArgument(noteIdParameter) { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong(noteIdParameter) ?: 0
            EditNoteLabelScreen(
                noteId = noteId,
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(createNoteScreenNavigationRoute) {
            val draftNoteLabels by homeScreenViewModel.draftNoteLabels.collectAsStateWithLifecycle()

            CreateNoteScreen(
                title = homeScreenViewModel.draftNoteTitle,
                onTitleChange = homeScreenViewModel::updateDraftNoteTitle,
                content = homeScreenViewModel.draftNoteContent,
                onContentChange = homeScreenViewModel::updateDraftNoteContent,
                labels = draftNoteLabels.map {
                    allLabels.find { label -> label.id == it }!!
                },
                onBackClick = { navController.navigateUp() },
                onNavigateToEditNoteLabels = {
                    navController.navigate(editDraftNoteLabelScreenNavigationRoute) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(editDraftNoteLabelScreenNavigationRoute) {
            val draftNoteLabels by homeScreenViewModel.draftNoteLabels.collectAsStateWithLifecycle()

            var query by rememberSaveable {
                mutableStateOf("")
            }

            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { padding ->
                EditNoteLabelList(
                    allLabels = allLabels,
                    selectedLabels = draftNoteLabels,
                    query = query,
                    onUpdateLabel = homeScreenViewModel::updateDraftNoteLabel,
                    onUpdateQuery = { query = it },
                    onCreateNewLabel = {
                        homeScreenViewModel.viewModelScope.launch {
                            val id = homeScreenViewModel.createLabel(Label(it))
                            homeScreenViewModel.updateDraftNoteLabel(id, true)
                        }
                    },
                    contentPadding = padding
                )
            }
        }
    }
}