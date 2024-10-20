package com.raineru.panatilihin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import com.raineru.panatilihin2.data.Note
import com.raineru.panatilihin2.navigation.HomeSubNavGraph
import com.raineru.panatilihin2.navigation.homeScreen
import com.raineru.panatilihin2.navigation.homeSubNavGraph
import com.raineru.panatilihin2.screen.NoteScreen
import com.raineru.panatilihin2.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start test code -----------------------------------------------
//        enableEdgeToEdge()

        setContent {
            PanatilihinTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeSubNavGraph
                ) {
                    homeSubNavGraph {
                        homeScreen(navController)

                        composable<Note> { backStackEntry ->
                            val note: Note = backStackEntry.toRoute()

                            val noteViewModel: NoteViewModel =
                                hiltViewModel<NoteViewModel, NoteViewModel.Factory>(
                                    creationCallback = { factory ->
                                        factory.create(
                                            title = note.title,
                                            content = note.content,
                                            id = note.id
                                        )
                                    }
                                )

                            NoteScreen(
                                titleTextFieldState = noteViewModel.noteTitleState,
                                contentTextFieldState = noteViewModel.noteContentState,
                                onBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}