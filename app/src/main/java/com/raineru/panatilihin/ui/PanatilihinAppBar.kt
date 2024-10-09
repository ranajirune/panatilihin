package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.raineru.panatilihin.R
import com.raineru.panatilihin.navigation.homeScreenNavigationRoute
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

// TODO: show the app bar when a note is selected even if the app bar is not visible
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanatilihinNoteSelectionAppBar(
    selectedNoteCount: Int,
    isInSelectionMode: Boolean,
    navController: NavController,
    showDropdownMenu: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onSelectionModeCancel: () -> Unit = {},
    onDropdownMenuClick: () -> Unit = {},
    onDropdownMenuDismiss: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    canCollapse: Boolean = true
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = currentBackStackEntry?.destination?.route != homeScreenNavigationRoute

    PanatilihinNoteSelectionAppBar(
        selectedNoteCount = selectedNoteCount,
        canNavigateBack = canNavigateBack,
        isInSelectionMode = isInSelectionMode,
        showDropdownMenu = showDropdownMenu,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        onSelectionModeCancel = onSelectionModeCancel,
        onDropdownMenuClick = onDropdownMenuClick,
        onDropdownMenuDismiss = onDropdownMenuDismiss,
        onDeleteClicked = onDeleteClicked,
        onNavigateBack = {
            navController.navigateUp()
        },
        canCollapse = canCollapse
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PanatilihinNoteSelectionAppBar(
    selectedNoteCount: Int,
    canNavigateBack: Boolean,
    isInSelectionMode: Boolean,
    showDropdownMenu: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onSelectionModeCancel: () -> Unit = {},
    onDropdownMenuClick: () -> Unit = {},
    onDropdownMenuDismiss: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    canCollapse: Boolean = true
) {
    TopAppBar(
        modifier = modifier,
        title = {
            // show selected note count only in the home screen and when in edit mode
            if (!canNavigateBack && isInSelectionMode) {
                Text("$selectedNoteCount")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.home_screen_app_bar_bg_color),
            scrolledContainerColor = colorResource(id = R.color.home_screen_app_bar_bg_color),
            titleContentColor = colorResource(id = R.color.home_screen_title_color)
        ),
        actions = {
            if (isInSelectionMode) {
                Box {
                    IconButton(onClick = onDropdownMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    DropdownMenu(
                        expanded = showDropdownMenu,
                        onDismissRequest = onDropdownMenuDismiss,
                        offset = DpOffset(x = 0.dp, y = (-20).dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.delete)) },
                            onClick = { onDeleteClicked() }
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            } else {
                if (isInSelectionMode) {
                    IconButton(onClick = onSelectionModeCancel) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                }
            }
        },
        scrollBehavior = if (canCollapse) {
            Log.d("PanatilihinAppBar", "PanatilihinHomeAppBar: canCollapse: $canCollapse")
            scrollBehavior
        } else {
            Log.d("PanatilihinAppBar", "PanatilihinHomeAppBar: canCollapse: $canCollapse")
            null
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onClearQuery: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        title = {
            BasicTextField(
                value = query,
                singleLine = true,
                onValueChange = onQueryChange,
                decorationBox = {
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (query.isEmpty()) {
                            Text(
                                stringResource(R.string.search_your_notes),
                                style = TextStyle.Default.copy(fontSize = 18.sp),
                                color = Color(0xFFAAA4A2)
                            )
                        }
                        it()
                    }
                }
            )
        },
        actions = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchNoteAppBarPreview() {
    PanatilihinTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SearchNoteAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                query = "",
                onQueryChange = {},
                onClearQuery = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PanatilihinAppBarPreview() {
    PanatilihinTheme {
        PanatilihinNoteSelectionAppBar(
            selectedNoteCount = 12,
            canNavigateBack = false,
            isInSelectionMode = true,
            showDropdownMenu = false,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanatilihinHomeAppBar(
    onMenuClick: () -> Unit,
    canCollapse: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
//    Box(modifier = Modifier) {
    TopAppBar(
        scrollBehavior = if (canCollapse) scrollBehavior else null,
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.horizontal_margin),
                end = dimensionResource(id = R.dimen.horizontal_margin),
                bottom = 0.dp,
                top = 8.dp
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
        title = {
            Text(
                stringResource(R.string.search_your_notes),
                style = TextStyle.Default.copy(fontSize = 18.sp),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.home_screen_app_bar_bg_color),
            scrolledContainerColor = colorResource(id = R.color.home_screen_app_bar_bg_color),
            titleContentColor = colorResource(id = R.color.home_screen_title_color)
        ),
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.open_navigation_drawer)
                )
            }
        }
    )
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PanatilihinHomeAppBarPreview() {
    PanatilihinTheme {
        PanatilihinHomeAppBar(
            onMenuClick = {},
            canCollapse = true,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}

@Composable
fun ExpandedPanatilihinAppBar(
    modifier: Modifier = Modifier
) {

}