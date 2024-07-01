package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun CoroutineScopeTestScreen() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar")
                    }
                }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Button(onClick = { }) {
                Text("Click me!")
            }
        }
    }
}


@Composable
fun CombineMergeZipTestScreen() {
    val editNoteViewModel: TestoViewModel = viewModel()

//    val textState by editNoteViewModel
////        .ownCombinedFlow
//        .collectAsStateWithLifecycle(
//            initialValue = listOf()
//        )

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
//            editNoteViewModel.changeOwnFlow1()
        }) {
            Text("CHANGE OWN FLOW 1")
        }
        Row {
            Button(onClick = {
//                val number = Random.nextInt(69, 420 + 1)
//                editNoteViewModel.changeOwnFlow2(number)
//                editNoteViewModel.changeOwnFlow2(9)
            }) {
                Text("FLOW 2")
            }
            Button(onClick = {
//                editNoteViewModel.changeOwnFlow2(8)
            }) {
                Text("NULL FLOW 2")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
//            items(textState) { number ->
//                Text("$number")
//            }
        }
    }
}

@Composable
fun MergeTestScreen() {
    val editNoteViewModel: TestoViewModel = viewModel()

//    val textState by editNoteViewModel
//        .mergedFlow
//        .collectAsStateWithLifecycle(
//            initialValue = 0
//        )
//
//    val textState2 by editNoteViewModel
//        .mergedFlow
//        .collectAsStateWithLifecycle(
//            initialValue = 999
//        )

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
//            editNoteViewModel.changeOwnFlow1()
        }) {
            Text("CHANGE OWN FLOW 1")
        }
        Row {
            Button(onClick = {
//                val number = Random.nextInt(69, 420 + 1)
//                editNoteViewModel.changeOwnFlow2(number)
//                editNoteViewModel.changeOwnFlow2(9)
            }) {
                Text("FLOW 2")
            }
            Button(onClick = {
//                editNoteViewModel.changeOwnFlow2(8)
            }) {
                Text("NULL FLOW 2")
            }
        }
        /*LazyColumn(
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            items(textState) { number ->
                Text("$number")
            }
        }*/
//        Text(textState.toString())
//        Text(textState2.toString())
    }
}


@Composable
fun LoginScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Perform login logic here */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}