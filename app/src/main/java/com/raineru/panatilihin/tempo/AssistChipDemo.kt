package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun AssistChipDemo(modifier: Modifier = Modifier) {
    AssistChip(
        modifier = modifier,
        onClick = { },
        label = { Text("Label A") }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipDemo(
    modifier: Modifier = Modifier
) {
    var selected by remember {
        mutableStateOf(false)
    }

    val intList: List<Int> = (1..10).toList()

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 3
    ) {
        intList.forEach { number ->
            FilterChip(
                modifier = modifier,
                selected = true,
                onClick = {  },
                label = {
                    Text("Label $number")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssistChipDemoPreview() {
    PanatilihinTheme {
        AssistChipDemo()
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipDemoPreview() {
    PanatilihinTheme {
        FilterChipDemo()
    }
}