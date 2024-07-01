package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun LabelScreen(
    labels: List<Label>,
    modifier: Modifier = Modifier,
    onInsertRandomLabelClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(onClick = onInsertRandomLabelClick) {
            Text(text = "INSERT RANDOM LABEL")
        }
        LazyColumn {
            items(labels) { label ->
                LabelEntry(label = label)
            }
        }
    }
}

@Composable
fun LabelEntry(
    label: Label
) {
    Text(label.name)
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelScreen() {
    PanatilihinTheme {
        LabelScreen(
            labels = listOf(
                Label("Label 1"),
                Label("Label 2"),
                Label("Label 3")
            )
        )
    }
}