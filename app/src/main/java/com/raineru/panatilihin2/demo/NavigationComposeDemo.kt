package com.raineru.panatilihin2.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

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