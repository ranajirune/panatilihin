package com.raineru.panatilihin

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raineru.panatilihin.navigation.editNoteScreenNavigationRoute
import com.raineru.panatilihin.navigation.homeScreenNavigationRoute
import com.raineru.panatilihin.navigation.navigateToEditNoteScreen
import com.raineru.panatilihin.navigation.noteIdParameter
import com.raineru.panatilihin.ui.EditNoteScreen
import com.raineru.panatilihin.ui.HomeScreen
import com.raineru.panatilihin.ui.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun PanatilihinNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeScreenNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = homeScreenNavigationRoute
        ) {
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            val notes by homeScreenViewModel.notes.collectAsStateWithLifecycle(
                lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
            )
            val labels by homeScreenViewModel.labels.collectAsStateWithLifecycle(
                lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
            )

            val coroutineScope = rememberCoroutineScope()

            HomeScreen(
                labels = labels,
                drawerState = drawerState,
                selectedNotes = homeScreenViewModel.selectedNotes,
                notes = notes,
                onNoteClick = {
                    if (!homeScreenViewModel.isInSelectionMode()) {
                        navController.navigateToEditNoteScreen(it)
                    } else {
                        homeScreenViewModel.noteClicked(it)
                    }
                },
                onMenuLabelClick = {

                },
                onMenuClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onCreateNewLabelClick = {

                },
                onEditLabelClick = {

                },
                onNoteLongPress = {
                    homeScreenViewModel.noteLongPressed(it)
                },
            )
        }

        composable(
            route = "$editNoteScreenNavigationRoute/{$noteIdParameter}",
            arguments = listOf(navArgument(noteIdParameter) { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong(noteIdParameter) ?: 0
            EditNoteScreen(noteId = noteId, onLabelClick = {})
        }
    }
}