package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun SelectedSurfaceDemo(modifier: Modifier = Modifier) {
    Column {
        Surface(
            selected = true,
            onClick = {  }
        ) {
            Text("Selected")
        }
        Surface(
            selected = false,
            onClick = {  }
        ) {
            Text("Selected")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedSurfaceDemo() {
    PanatilihinTheme {
        SelectedSurfaceDemo()
    }
}