package com.raineru.panatilihin.tempo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class BasicTextFieldDemo {

    @Preview(showBackground = true)
    @Composable
    fun BasicTextFieldDecoratorSample() {
        // Demonstrates how to use the decorator API on BasicTextField
        val state = rememberTextFieldState("Hello, World!")
        BasicTextField(
            state = state,
            decorator = { innerTextField ->
                // Because the decorator is used, the whole Row gets the same behaviour as the internal
                // input field would have otherwise. For example, there is no need to add a
                // `Modifier.clickable` to the Row anymore to bring the text field into focus when user
                // taps on a larger text field area which includes paddings and the icon areas.
                Row(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.MailOutline, contentDescription = "Mail Icon")
                    Spacer(Modifier.width(16.dp))
                    innerTextField()
                }
            }
        )
    }
}
