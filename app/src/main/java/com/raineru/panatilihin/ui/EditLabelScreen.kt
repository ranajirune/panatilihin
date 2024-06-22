package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun EditLabelScreen(
    labels: List<Label>,
    onLabelChange: (Label) -> Unit,
    onDeleteLabel: (Long) -> Unit,
    onCreateLabel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    var showDialog by rememberSaveable { mutableStateOf(false) }

    var toDeleteLabelId by rememberSaveable { mutableLongStateOf(0) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDelete = {
                onDeleteLabel(toDeleteLabelId)
                showDialog = false
            },
            onDismissRequest = {
                toDeleteLabelId = 0
                showDialog = false
            },
            onCancel = {
                toDeleteLabelId = 0
                showDialog = false
            }
        )
    }

    LazyColumn(modifier = modifier) {
        item {
            var label by rememberSaveable {
                mutableStateOf("")
            }

            val focusRequester = remember { FocusRequester() }

            LabelEntry(
                label = label,
                leadingIcon = Icons.Default.Add,
                inFocusLeadingIcon = Icons.Default.Clear,
                inFocusTrailingIcon = Icons.Default.Check,
                onInFocusTrailingIconClick = {
                    onCreateLabel(label)
                    label = ""
                    focusManager.clearFocus()
                },
                onInFocusLeadingIconClick = {
                    // Cancel creation of a label
                    label = ""
                    focusManager.clearFocus()
                },
                onLabelChange = {
                    label = it
                },
                focusRequester = focusRequester
            )
        }

        labels.forEach { l ->
            item(key = l.id) {
                var text by remember {
                    mutableStateOf(l.name)
                }

                val focusRequester = remember { FocusRequester() }

                LabelEntry(
                    label = text,
                    leadingIcon = Icons.Default.MailOutline,
                    inFocusLeadingIcon = Icons.Default.Delete,
                    trailingIcon = Icons.Default.Edit,
                    inFocusTrailingIcon = Icons.Default.Check,
                    onLabelChange = {
                        text = it
                        onLabelChange(l.copy(name = it))
                    },
                    onInFocusTrailingIconClick = {
                        focusManager.clearFocus()
                    },
                    onInFocusLeadingIconClick = {
                        toDeleteLabelId = l.id
                        showDialog = true
                        focusManager.clearFocus()
                    },
                    focusRequester = focusRequester,
                    onTrailingIconClick = {
                        focusRequester.requestFocus()
                    }
                )
            }
        }
    }
}

@Composable
fun LabelEntry(
    label: String,
    onLabelChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onLeadingIconClick: (() -> Unit)? = null,
    onInFocusLeadingIconClick: (() -> Unit)? = null,
    onInFocusTrailingIconClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    inFocusLeadingIcon: ImageVector? = null,
    inFocusTrailingIcon: ImageVector? = null,
) {
    var isInFocus by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        HorizontalDivider(
            color = if (isInFocus) {
                DividerDefaults.color
            } else {
                Color.Transparent
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isInFocus) {
                LabelEntryIconButton(
                    icon = inFocusLeadingIcon,
                    onClick = onInFocusLeadingIconClick
                )
            } else {
                LabelEntryIconButton(
                    icon = leadingIcon,
                    onClick = onLeadingIconClick
                )
            }

//            var isLabelValid by remember { mutableStateOf(true) }

//            var text by remember { mutableStateOf(label) }

            // TODO mimic keep notes invalid label behavior
            BasicTextField(
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                value = label,
                onValueChange = {
                    onLabelChange(it)
                    /*if (label.length == 50 && it.length == 51) {
                        // Char limit at max and user pressed a key. Show error
                        isLabelValid = false
                    } else if (it.length > 50) {
                        // User pasted text that is too long
                        isLabelValid = false
                        val cappedText = it.substring(0, 50)
                        onValueChange(cappedText)
                    } else (it.length <= 50) {
                        onValueChange(it)
                        isLabelValid = true
                    }*/
                },
                modifier = Modifier
                    .onFocusChanged {
                        isInFocus = it.isFocused
                    }
                    .weight(1f)
                    .focusRequester(focusRequester),
                singleLine = true,
                maxLines = 1,
                decorationBox = { innerTextField ->
                    if (label.isEmpty()) {
                        Box {
                            Text(
                                text = "Create a new label",
                                color = Color.Gray,
                                style = TextStyle.Default.copy(fontSize = 16.sp)
                            )
                            innerTextField()
                        }
                    } else {
                        /*if (!isLabelValid && isInFocus) {
                            Column {
                                innerTextField()
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.invalid_label_text),
                                    color = MaterialTheme.colorScheme.error,
                                    style = TextStyle(
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        } else {
                            innerTextField()
                        }*/
                        innerTextField()
                    }
                }
            )

            if (isInFocus) {
                LabelEntryIconButton(
                    icon = inFocusTrailingIcon,
                    onClick = onInFocusTrailingIconClick
                )
            } else {
                LabelEntryIconButton(
                    icon = trailingIcon,
                    onClick = onTrailingIconClick
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

@Composable
fun LabelEntryIconButton(
    icon: ImageVector?,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    if (onClick != null) {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    } else {
        if (icon != null) {
            EmptyBox {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            EmptyBox {}
        }
    }
}

@Composable
private fun EmptyBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .size(40.dp)
    ) {
        content()
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