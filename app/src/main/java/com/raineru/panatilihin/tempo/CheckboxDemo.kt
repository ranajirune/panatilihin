package com.raineru.panatilihin.tempo

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun CheckboxDemo(modifier: Modifier = Modifier) {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CheckboxDemoPreview() {
    PanatilihinTheme {
        CheckboxDemo()
    }
}