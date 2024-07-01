package com.raineru.panatilihin.tempo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputTransformationScreen(
    modifier: Modifier = Modifier
) {
    /*BasicTextField(
        textStyle = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
        state = rememberTextFieldState(),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        inputTransformation = InputTransformation.maxLength(6)
            .then(
                InputTransformation.allCaps(
                    Locale.current
                )
            ),
        outputTransformation = {
            // Pad the text with placeholder chars if too short
            // ··· ···
            val padCount = 6 - length
            repeat(padCount) {
                append('·')
            }

            // 123 456
            if (length > 3) insert(3, " ")
        },
        decorator = {
            Box(
                modifier = Modifier
                    .padding(10.dp)
//                    .background(Color.Red)
            ) {
                it()
            }
        }
    )*/
}

@Preview
@Composable
fun InputTransformationScreenPreview(modifier: Modifier = Modifier) {
    InputTransformationScreen()
}