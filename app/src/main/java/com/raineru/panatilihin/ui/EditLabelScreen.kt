package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun EditLabelScreen(
    labels: List<Label>,
    onValueChange: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(labels) { label ->
            LabelEntry(
                label = label.name,
                onValueChange = {
                    onValueChange(label.id, it)
                }
            )
        }
    }
}

@Composable
fun LabelEntry(
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    var isInFocus by remember {
        mutableStateOf(false)
    }
    Column {
        HorizontalDivider(
            color = if (isInFocus) {
                DividerDefaults.color
            } else {
                Color.Transparent
            }
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isInFocus) {
                IconButton(
                    onClick = {
                        onDeleteClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            val focusRequester = remember { FocusRequester() }

            BasicTextField(
                value = label,
                onValueChange = onValueChange,
                modifier = Modifier
                    .onFocusChanged { isInFocus = it.isFocused }
                    .weight(1f)
                    .focusRequester(focusRequester),
                singleLine = true,
                maxLines = 1
            )

            val focusManager = LocalFocusManager.current

            IconButton(
                onClick = {
                    if (!isInFocus) {
                        focusRequester.requestFocus()
                    } else {
                        focusManager.clearFocus()
                        onConfirmClick()
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = if (isInFocus) {
                        Icons.Filled.Check
                    } else {
                        Icons.Filled.Edit
                    },
                    contentDescription = null
                )
            }
        }
        HorizontalDivider(
            color = if (isInFocus) {
                DividerDefaults.color
            } else {
                Color.Transparent
            }
        )
    }
}

@Preview(showBackground = true, name = "In Focus")
@Composable
fun LabelEntryPreview() {
    PanatilihinTheme {
        LabelEntry("A Label", onValueChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EditLabelScreenPreview() {
    PanatilihinTheme {
        val labelList = remember {
            mutableStateListOf(
                Label(name = "1st Label", id = 1),
                Label(name = "2nd Label", id = 2),
                Label(name = "3rd Label", id = 3)
            )
        }

        Box(modifier = Modifier.padding(vertical = 8.dp)) {
            EditLabelScreen(
                labels = labelList,
                modifier = Modifier.fillMaxSize(),
                onValueChange = { id, newValue ->
                    val index = labelList.indexOfFirst { it.id == id }
                    labelList[index] = labelList[index].copy(name = newValue)
                }
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(stringResource(R.string.delete_label_confirm_button_text))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(stringResource(R.string.delete_label_dismiss_button_text))
            }
        },
        title = { Text(stringResource(R.string.delete_label_title)) },
        text = { Text(stringResource(R.string.delete_label_confirmation_text)) }
    )
}

@Preview(showBackground = true, name = "Delete Confirmation Dialog")
@Composable
fun DeleteConfirmationDialogPreview(modifier: Modifier = Modifier) {
    PanatilihinTheme {
        var showDialog by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Show Dialog")
            }
            when {
                showDialog -> DeleteConfirmationDialog(
                    onDismissRequest = {
                        showDialog = false
                        Log.d("EditLabelScreen", "onDismissRequest")
                    },
                    onDelete = {
                        showDialog = false
                        Log.d("EditLabelScreen", "onDelete")
                    },
                    onCancel = {
                        showDialog = false
                        Log.d("EditLabelScreen", "onCancel")
                    }
                )
            }
        }
    }
}