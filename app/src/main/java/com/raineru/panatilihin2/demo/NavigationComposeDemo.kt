package com.raineru.panatilihin2.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import javax.inject.Inject

// Define a home route that doesn't take any arguments
@Serializable
object Home

// Define a profile route that takes an ID
@Serializable
data class Profile(val id: String)

@Preview(showBackground = true)
@Composable
private fun NavigationComposeDemo() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            Column {
                Text("Home")
                Button(
                    onClick = {
                        navController.navigate(Profile("123"))
                    }
                ) {
                    Text("Go to profile 123")
                }
            }
        }

        composable<Profile> { backStackEntry ->
            val profile: Profile = backStackEntry.toRoute()
            Column {
                Text("I'm Profile ${profile.id}")
                Button(
                    onClick = {
                        navController.navigate(Home)
                    }
                ) {
                    Text("Go to Home.")
                }
            }
        }
    }
}

@Serializable
object FirstRoom

@Serializable
object SecondRoom

// Call composable on MainActivity for testing
@Composable
fun NestedNavigationDemo() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        navigation<Home>(
            startDestination = FirstRoom,
        ) {
            composable<FirstRoom> {
                val parentEntry = remember { navController.getBackStackEntry<Home>()}
                val sharedViewModel = hiltViewModel<SharedViewModel>(parentEntry)

                val number by sharedViewModel.sharedNumber.collectAsState()

                Column {
                    Text("Welcome to the first room.")
                    Text("The shared number is $number")
                    Button(
                        onClick = {
                            sharedViewModel.incrementSharedNumber()
                        }
                    ) {
                        Text("Increment Number")
                    }
                    Button(
                        onClick = {
                            navController.navigate(SecondRoom)
                        }
                    ) {
                        Text("Go to second room.")
                    }
                }
            }
            composable<SecondRoom> {
                val parentEntry = remember { navController.getBackStackEntry<Home>()}
                val sharedViewModel = hiltViewModel<SharedViewModel>(parentEntry)

                val number by sharedViewModel.sharedNumber.collectAsState()

                Column {
                    Text("Welcome to the second room.")
                    Text("The shared number is $number")
                    Button(
                        onClick = {
                            sharedViewModel.incrementSharedNumber()
                        }
                    ) {
                        Text("Increment Number")
                    }
                }
            }
        }
    }
}

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _sharedNumber = MutableStateFlow(0)
    val sharedNumber: StateFlow<Int> = _sharedNumber.asStateFlow()

    fun incrementSharedNumber() {
        _sharedNumber.value++
    }
}