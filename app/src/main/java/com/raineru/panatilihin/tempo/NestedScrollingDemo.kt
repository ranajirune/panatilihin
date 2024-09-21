package com.raineru.panatilihin.tempo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.CollapsingAppBarNestedScrollConnection
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

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

// emulating Google Keep top app bar settling behavior
class AnotherNestedScrollingDemo2 {

    @Preview
    @Composable
    fun AnotherNestedScrollingDemo2Preview() {
        var offset by remember {
            mutableIntStateOf(0)
        }

        val density = LocalDensity.current

        val settledOffsetThreshold = with(density) {
            2.dp.roundToPx()
        }

        val maxOffset = with(LocalDensity.current) {
            400.dp.roundToPx()
        }

        val isHalfway by remember(offset) {
            val halfway = if (offset.absoluteValue >= (maxOffset * 0.5f)) {
                true
            } else {
                false
            }
            mutableStateOf(halfway)
        }

        var offsetBeforeSettled by remember {
            mutableIntStateOf(0)
        }

        val connection = remember {
            object : NestedScrollConnection {
                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    if (source == NestedScrollSource.SideEffect) {
                        // not lowerbound e.g (-222 instead of -224)
                        if ((maxOffset + offset) <= settledOffsetThreshold) {
                            if (offset != -maxOffset) {
                                offsetBeforeSettled = offset
                                Log.d("NestedScrollingDemo", "offsetBeforeSettled: $offset")
                            }
                            offset = -maxOffset
                        }

                        if (offset.absoluteValue <= settledOffsetThreshold) {
                            if (offset != 0) {
                                offsetBeforeSettled = offset
                                Log.d("NestedScrollingDemo", "offsetBeforeSettled: $offset")
                            }
                            offset = 0
                        }
                    }

                    return super.onPostScroll(consumed, available, source)
                }

                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val message = "onPreScroll: available=$available, source=$source"
                    Log.d("NestedScrollingDemo", message)
                    val delta = available.y.roundToInt()
                    offset += delta
                    offset = offset.coerceIn(-maxOffset, 0)
                    return super.onPreScroll(available, source)
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    val message = "onPostFling: consumed=$consumed, available=$available"
                    Log.d("NestedScrollingDemo", message)
                    return super.onPostFling(consumed, available)
                }

                override suspend fun onPreFling(available: Velocity): Velocity {
                    val message = "onPreFling: available=$available"
                    Log.d("NestedScrollingDemo", message)
                    return super.onPreFling(available)
                }
            }
        }

        val snapLayout = remember {
            object : SnapLayoutInfoProvider {

                override fun calculateSnapOffset(velocity: Float): Float {
                    val anOffset = if (offset <= -maxOffset) {
                        0f
                    } else if (offset.absoluteValue >= (maxOffset * 0.5f)) {
                        maxOffset + offset.toFloat()
                    } else {
                        offset.toFloat()
                    }
                    return anOffset
                }
            }
        }

        val snapFlingBehavior = rememberSnapFlingBehavior(snapLayout)

        Box(Modifier.nestedScroll(connection)) {
            LazyColumn(
                flingBehavior = snapFlingBehavior
            ) {
                items((1..100).toList()) {
                    ListItem(headlineContent = { Text(it.toString()) })
                }
            }

            Box(
                Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Red)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Blue)
                        .align(Alignment.BottomCenter)
                )
            }


            Text(
                "offset: $offset",
                modifier = Modifier
                    .offset {
                        IntOffset(0, offset)
                    }
                    .align(Alignment.BottomCenter)
                    .width(100.dp)
                    .height(50.dp)
                    .background(Color.Green)
            )

            Text(
                "halfway: $isHalfway - offset: $offset," +
                        " settledOffsetThreshold: $settledOffsetThreshold," +
                        " offsetBeforeSettled: $offsetBeforeSettled",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green)
            )
        }
    }
}