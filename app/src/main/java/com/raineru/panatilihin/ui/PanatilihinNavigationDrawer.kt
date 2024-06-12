package com.raineru.panatilihin.ui

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
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import kotlinx.coroutines.launch

@Composable
fun PanatilihinNavigationDrawer(
    labels: List<Label>,
    modifier: Modifier = Modifier,
    onLabelClick: (Long) -> Unit = {},
    onCreateNewLabelClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    drawerState: DrawerValue = DrawerValue.Closed,
    content: @Composable () -> Unit
) {
    val dState = rememberDrawerState(initialValue = drawerState)
    val scope = rememberCoroutineScope()
    var selectedLabelIndex by rememberSaveable { mutableIntStateOf(-1) }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = dState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(30.dp))
                Text(
                    stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.height(30.dp))

                if (labels.isNotEmpty()) {
                    HorizontalDivider()
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Labels")
                        TextButton(onClick = {
                            scope.launch {
                                dState.close()
                            }
                            onEditClick()
                        }) {
                            Text("Edit")
                        }
                    }
                }

                labels.forEachIndexed { index, label ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = label.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selected = selectedLabelIndex == index,
                        onClick = {
                            selectedLabelIndex = index
                            scope.launch {
                                dState.close()
                            }
                            onLabelClick(label.id)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = label.name
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
                    onClick = onCreateNewLabelClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create new label"
                        )
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                if (labels.isNotEmpty()) {
                    HorizontalDivider()
                }
            }
        },
        content = content
    )
}

@Preview(
    showBackground = true,
    name = "Navigation Drawer with labels"
)
@Composable
fun NavDrawerPreview() {
    PanatilihinTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PanatilihinNavigationDrawer(
                labels = listOf(
                    Label("Label 1"),
                    Label("Label 2"),
                    Label("Label 3"),
                    Label("Label 4 With A Very Very Long Long Long Long John Name"),
                ),
                drawerState = DrawerValue.Open
            ) {

            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Navigation Drawer without labels"
)
@Composable
fun NavDrawerWithoutLabelsPreview() {
    PanatilihinTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PanatilihinNavigationDrawer(
                labels = listOf(),
                drawerState = DrawerValue.Open
            ) {

            }
        }
    }
}