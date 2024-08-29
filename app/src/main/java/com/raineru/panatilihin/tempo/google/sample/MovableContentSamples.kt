package com.raineru.panatilihin.tempo.google.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemView(@Suppress("UNUSED_PARAMETER") userId: Int) {
}
typealias Item = Int

@Suppress("unused")
@Composable
fun MovableContentColumnRowSample(vertical: Boolean, content: @Composable () -> Unit) {
    val movableContent = remember(content as Any) { movableContentOf(content) }
    if (vertical) {
        Column {
            movableContent()
        }
    } else {
        Row {
            movableContent()
        }
    }
}

@Preview
@Composable
fun MovableContentTest() {
    MovableContentColumnRowSample(false) {
        Column {
            var aNumber by rememberSaveable {
                mutableIntStateOf(0)
            }
            Button(onClick = { aNumber++ }) {
                Text("I'm number $aNumber")
            }
        }
    }
}

@Suppress("unused")
@Composable
fun MovableContentMultiColumnSample(items: List<Item>) {
    val itemMap = remember {
        mutableMapOf<Item, @Composable () -> Unit>()
    }
    val movableItems =
        items.map { item -> itemMap.getOrPut(item) { movableContentOf { ItemView(item) } } }
    val itemsPerColumn = 10
    val columns = items.size / itemsPerColumn + (if (items.size % itemsPerColumn == 0) 0 else 1)
    Row {
        repeat(columns) { column ->
            Column {
                val base = column * itemsPerColumn
                val end = minOf(base + itemsPerColumn, items.size)
                for (index in base until end) {
                    movableItems[index]()
                }
            }
        }
    }
}