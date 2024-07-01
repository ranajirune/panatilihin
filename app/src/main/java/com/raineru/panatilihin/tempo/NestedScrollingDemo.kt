package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedScrollingDemo(modifier: Modifier = Modifier) {
    val numbers = (1..100).map { Random.nextInt(1, 10) * it }

    val AppBarHeight = 56.dp
    val Purple40 = Color(0xFF6650a4)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val appBarMaxHeightPx = with(LocalDensity.current) { AppBarHeight.roundToPx() }
        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }

        Box(Modifier.nestedScroll(connection)) {
            LazyColumn(contentPadding = PaddingValues(top = AppBarHeight)) {
                items(numbers) {
                    ListItem(headlineContent = { Text("Item $it") })
                }
            }

            TopAppBar(
                modifier = Modifier.offset { IntOffset(0, connection.appBarOffset) },
                title = { Text(text = "Jetpack Compose") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                )
            )
        }
    }
}

private class CollapsingAppBarNestedScrollConnection(
    val appBarMaxHeight: Int
) : NestedScrollConnection {

    var appBarOffset: Int by mutableIntStateOf(0)
        private set

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.toInt()
        val newOffset = appBarOffset + delta
        val previousOffset = appBarOffset
        appBarOffset = newOffset.coerceIn(-appBarMaxHeight, 0)
        val consumed = appBarOffset - previousOffset
        return Offset(0f, consumed.toFloat())
    }
}