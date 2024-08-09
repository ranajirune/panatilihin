package com.raineru.panatilihin.tempo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.raineru.panatilihin.ui.CollapsingAppBarNestedScrollConnection
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

val Purple40 = Color(0xFF6650a4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedScrollingDemo(
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val appBarMaxHeightPx =
            with(LocalDensity.current) { TopAppBarDefaults.TopAppBarExpandedHeight.roundToPx() }
        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }
        val density = LocalDensity.current
        val spaceHeight by remember(density) {
            derivedStateOf {
                with(density) {
                    (appBarMaxHeightPx + connection.appBarOffset).toDp()
                }
            }
        }

        Box(Modifier.nestedScroll(connection)) {
            Column {
                Spacer(
                    Modifier
                        .height(spaceHeight)
                )
                LazyColumn {
                    items(20) {
                        ListItem(
                            headlineContent = {
                                Text("Item $it")
                            },
                            modifier = Modifier.clickable { }
                        )
                    }
                }
            }


            TopAppBar(
                title = {
                    Text("Jetpack Compose")
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.offset { IntOffset(0, connection.appBarOffset) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NestedScrollingDemoPreview(modifier: Modifier = Modifier) {
    PanatilihinTheme {
        NestedScrollingDemo()
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
//        return Offset(0f, consumed.toFloat())
        return Offset.Zero
    }
}

@Composable
private fun NestedScrollingDemo2(
) {

}

@Preview(showBackground = true)
@Composable
private fun NestedScrollingDemo2Preview() {
    PanatilihinTheme {
        NestedScrollingDemo2()
    }
}

enum class States {
    EXPANDED,
    COLLAPSED
}

@Composable
private fun MyBottomSheet(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
) {
    /*val swipeableState = rememberSwipeableState(initialValue = States.EXPANDED)

    Box(
        Modifier
            .swipeable()
    ) {
        Column(
            Modifier
            .fillMaxHeight()
        ) {
            header()
            Box(
                Modifier.fillMaxWidth()
            ) {
                body()
            }
        }
    }*/
}