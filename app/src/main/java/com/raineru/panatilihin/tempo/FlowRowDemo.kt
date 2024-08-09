package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRowColumnDemo(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label 1")
            }
        )
        FilterChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label 2")
            }
        )
        FilterChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label 3")
            }
        )
        FilterChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label 4")
            }
        )
        FilterChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label 5")
            }
        )
    }
}

@Preview
@Composable
fun FlowRowPreview() {
    PanatilihinTheme {
        FlowRowColumnDemo(modifier = Modifier.height(512.dp))
    }
}