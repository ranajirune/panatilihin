package com.raineru.panatilihin2.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun NoteScreen(
    titleTextFieldState: TextFieldState,
    contentTextFieldState: TextFieldState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Surface {
            Row(Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    PaddingValues(
                        start = 20.dp,
                        end = 8.dp
                    )
                ),
        ) {
            BasicTextField(
                state = titleTextFieldState,
                modifier = Modifier
                    .padding(top = 36.dp)
                    .fillMaxWidth(),
//                lineLimits = TextFieldLineLimits.SingleLine,
                decorator = {
                    Box {
                        if (titleTextFieldState.text == "") {
                            Text(
                                "Title",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        it()
                    }
                },
                textStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            BasicTextField(
                state = contentTextFieldState,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                decorator = {
                    Box {
                        if (contentTextFieldState.text == "") {
                            Text(
                                "Note",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        it()
                    }
                },
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteScreenPreview() {
    PanatilihinTheme {
        NoteScreen(
            onBack = {},
            modifier = Modifier,
            titleTextFieldState = rememberTextFieldState(
                initialText = stringResource(id = R.string.title_initial_text_large)
//                initialText = ""
            ),
            contentTextFieldState = rememberTextFieldState(
                initialText = stringResource(id = R.string.note_initial_text_large)
//                initialText = ""
            )
        )
    }
}