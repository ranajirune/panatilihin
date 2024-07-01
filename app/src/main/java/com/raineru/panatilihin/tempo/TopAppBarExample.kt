package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                /*colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),*/
                title = {
//                    Text("Small Top App Bar")
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                Text(innerPadding.toString())
            }
            item {
                ScrollableContent()
            }
        }
    }
}

@Composable
fun ScrollableContent(modifier: Modifier = Modifier) {
    Text(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed efficitur, risus ut pellentesque molestie, arcu justo consequat nunc, ut iaculis ante nisi sed urna. Ut vitae mi a lectus faucibus bibendum. Vivamus tristique interdum metus, et porta ante aliquet suscipit. Pellentesque ultrices libero quis ante viverra, sit amet pretium augue dignissim. Fusce quam eros, congue non justo eget, tincidunt volutpat diam. Suspendisse potenti. Nam in mauris luctus, maximus orci aliquet, lacinia augue. Curabitur eget ultrices mi. Praesent a leo facilisis dolor gravida rhoncus.\n" +
                "\n" +
                "In porttitor vitae eros quis ultricies. Nullam erat nibh, consequat sed feugiat et, vehicula sit amet eros. Vestibulum aliquet fringilla purus in tempor. Sed iaculis lacus nec malesuada vulputate. Morbi nec elit tincidunt, pretium nunc vel, sagittis augue. Integer blandit enim vitae fringilla convallis. Fusce vehicula metus vel odio auctor finibus a eget est.\n" +
                "\n" +
                "Curabitur dictum malesuada mi a fermentum. Sed sodales hendrerit turpis at placerat. Donec eu fringilla metus. Praesent et diam at urna facilisis scelerisque. Mauris enim sapien, pellentesque et placerat sed, dictum et leo. Duis justo dolor, condimentum ornare nibh nec, semper mattis tortor. Nullam eget mollis odio.\n" +
                "\n" +
                "Morbi tincidunt mattis lorem ut malesuada. Mauris feugiat, ex et vehicula varius, ligula diam volutpat massa, nec dignissim elit velit sed augue. Vestibulum euismod blandit tellus et iaculis. In ut rhoncus massa. Aliquam aliquet augue id turpis vestibulum, volutpat efficitur metus gravida. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam facilisis, massa a mollis iaculis, libero ligula interdum augue, id porttitor lorem orci nec dolor. Mauris tellus ipsum, suscipit a nibh quis, egestas maximus augue. Mauris vitae est commodo, auctor nibh ut, posuere lacus. Nulla sed lectus eleifend lectus tincidunt feugiat et ut quam. Proin tempus dolor id est porta, eget maximus odio sodales. Phasellus quis justo non orci rhoncus cursus et sed ipsum. Nullam at justo nec enim pulvinar dignissim eu vitae lectus. Pellentesque condimentum sapien sed posuere aliquet. Sed nec lectus id est porttitor imperdiet.\n" +
                "\n" +
                "Integer lacinia pharetra suscipit. Proin congue massa vehicula ex tincidunt rutrum. Aenean rhoncus dictum ipsum. Maecenas arcu ligula, tempor at ligula nec, consectetur posuere velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque euismod, turpis non mollis vulputate, ex nulla placerat tellus, non convallis diam dui ac enim. Cras suscipit odio ac purus aliquam, in blandit tellus condimentum. Donec eget nisl efficitur leo convallis sollicitudin at ac justo. Duis pretium sollicitudin elit id eleifend. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque condimentum ultricies malesuada. In erat nisi, tincidunt eget enim quis, pharetra rutrum justo. Donec lacus ipsum, vehicula eu est a, laoreet iaculis ante.\n" +
                "\n" +
                "Aliquam a lacus elit. Nam sed mauris porttitor, rutrum lacus nec, hendrerit dolor. Duis et leo convallis, mattis eros quis, elementum odio. Sed maximus nunc ac enim suscipit fringilla. In ornare metus sem, et porta tellus pharetra nec. Nam pharetra, elit in semper venenatis, mauris elit rutrum turpis, eget euismod nulla mi eget lorem. Nam eu congue eros. Praesent bibendum libero id suscipit semper. Sed molestie augue id ex eleifend, rutrum pellentesque lacus euismod. Praesent at ante eu mauris tempor porttitor. Nam dapibus euismod ornare.\n" +
                "\n" +
                "Nam posuere velit sem, quis tristique libero sagittis eu. Phasellus vestibulum, sapien sed sollicitudin placerat, tortor turpis gravida lacus, egestas commodo sem augue vel felis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In pulvinar massa ut ornare posuere. Nullam ac pellentesque lectus. Vestibulum interdum quam id molestie accumsan. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Duis ultricies egestas ligula, sed tincidunt sapien iaculis id. Duis et tempus urna. Sed at nisl finibus, euismod mauris a, tempor lacus. Maecenas quis vestibulum nibh. Ut semper lacus tellus, ut ultrices diam molestie sed.\n" +
                "\n" +
                "Ut nec justo finibus, pharetra nisl vel, sagittis purus. Pellentesque eget vestibulum enim. Mauris varius nisl in tortor posuere semper. Quisque sed sapien sed velit viverra sodales. Maecenas vel facilisis nibh, sed interdum sapien. Sed tristique vestibulum blandit. Fusce vel nulla odio. Cras est purus, hendrerit id hendrerit quis, ornare in purus. Maecenas non elit blandit, bibendum lorem sed, malesuada eros. Integer ante arcu, vulputate at nisi at, pulvinar pellentesque augue. Sed dictum risus a pellentesque elementum.\n" +
                "\n" +
                "Cras scelerisque eget nisl eu ultrices. Sed efficitur tellus et enim pretium aliquam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc orci justo, posuere vel ante vel, ornare ultricies erat. Sed quam ex, sollicitudin id auctor nec, egestas vel lectus. Sed sed est imperdiet, vulputate mi et, condimentum tellus. Morbi justo turpis, malesuada nec nisi et, ultrices scelerisque nulla. Duis ac turpis mollis, pellentesque neque et, imperdiet mauris. Curabitur scelerisque eros urna, in pulvinar nibh placerat at. Aliquam nisi turpis, viverra et pellentesque at, lacinia tempor nunc. Nam vitae venenatis velit, eu vulputate enim. Aenean vulputate dolor libero, eget posuere enim tincidunt nec. Suspendisse sit amet fringilla leo. Morbi faucibus nec orci eget accumsan.\n" +
                "\n" +
                "Ut congue, orci at luctus facilisis, elit neque tempor dolor, sit amet dignissim elit mi et risus. Donec sollicitudin faucibus efficitur. Sed quam justo, condimentum nec justo sed, elementum accumsan nunc. In id mauris dignissim, hendrerit orci id, iaculis risus. Donec tellus massa, viverra id ultrices non, lobortis sit amet leo. Nullam aliquam, libero nec tincidunt porta, velit libero rhoncus felis, in sollicitudin diam mauris quis urna. Phasellus cursus consequat mi, in suscipit mauris. Sed finibus dui in urna dignissim suscipit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed porta id libero id tincidunt. Cras varius mattis feugiat. raineru"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Centered Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) {
                Text(
                    "Item $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBarExample() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Medium Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                ScrollableContent()
            }
        }
    }
}