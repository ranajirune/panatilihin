package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Preview(showBackground = true)
@Composable
fun CaptureFreeFocusPreview() {
    PanatilihinTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                val focusRequester = remember { FocusRequester() }
                Capture(focusRequester)

                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))

                var text by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = text, onValueChange = { text = it },
                )
                Button(onClick = { focusRequester.requestFocus() }) {
                    Text("Request focus on TextField")
                }
            }
        }
    }
}

@Composable
private fun Capture(
    focusRequester: FocusRequester
) {
    var text by remember { mutableStateOf("") }
    // [START android_compose_touchinput_focus_capture]

    var isError by remember { mutableStateOf(false) }

    TextField(
        value = text,
        isError = isError,
        onValueChange = {
            text = it

            if (it.length < 3) {
                focusRequester.captureFocus()
                isError = true
            } else {
                focusRequester.freeFocus()
                isError = false
            }
        },
        supportingText = {
            if (isError) {
                Text(
                    text = "Enter a valid input 3 characters or more",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.focusRequester(focusRequester)
    )
}