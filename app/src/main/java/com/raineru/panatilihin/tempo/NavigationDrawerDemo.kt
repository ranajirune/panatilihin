package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerDemo(
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val starterPokemons = listOf(
        "Bulbasaur",
        "Charmander",
        "Squirtle"
    )
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Panatilihin",
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.height(30.dp))

                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Labels")
                    TextButton(onClick = {  }) {
                        Text("I'm a texto")
                    }
                }
                starterPokemons.forEachIndexed { index, pokemon ->
                    NavigationDrawerItem(
                        label = {
                            Text(pokemon)
                        },
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Filled.DateRange,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                NavigationDrawerItem(
                    label = {
                        Text("Create new label")
                    },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Navigation Drawer") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Text("Navigation Drawer Demo")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationDrawerDemoPreview() {
    PanatilihinTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavigationDrawerDemo()
        }
    }
}