package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorUI() {
    var expression by remember { mutableStateOf("expression") }
    var result by remember { mutableStateOf("result") }

    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf(".", "0", "=", "+")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = expression,
                style = TextStyle.Default.copy(fontSize = 24.sp, color = Color.Gray)
            )
            Text(
                text = result,
                style = TextStyle.Default.copy(fontSize = 48.sp, color = Color.Black)
            )
        }

        // Button grid
        Column(modifier = Modifier.weight(2f)) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { buttonText ->
                        CalculatorButton(
                            text = buttonText,
                            onClick = {
//                                onButtonClick(buttonText, expression, result)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(text) },
        modifier = Modifier
//            .aspectRatio(1f)
//            .weight(1f)
//            .padding(8.dp)
    ) {
        Text(text = text, style = TextStyle.Default.copy(fontSize = 24.sp))
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorUiPreview() {
    CalculatorUI()
}