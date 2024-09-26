@file:OptIn(ExperimentalMaterial3Api::class)

package com.raineru.panatilihin.tempo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.CollapsingAppBarNestedScrollConnection
import com.raineru.panatilihin.ui.theme.PanatilihinTheme
import kotlin.math.absoluteValue

@Composable
fun WindowInsetsDemo() {
    val density = LocalDensity.current

    val systemBarsTopPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    val connection by remember(systemBarsTopPadding) {
        var offsetLowerBound: Int

        val maxHeight = with(density) {
            offsetLowerBound = systemBarsTopPadding.roundToPx()

            Log.d("WindowInsetsDemo", "offsetLowerBound: $offsetLowerBound")
            Log.d("WindowInsetsDemo", "systemBarsTopPadding: $systemBarsTopPadding")

            SearchBarDefaults.InputFieldHeight.roundToPx() +
                    8.dp.roundToPx()
        }
//        Log.d("WindowInsetsDemo", "systemBarPx: $systemBarPx")
        mutableStateOf(
            CollapsingAppBarNestedScrollConnection(
                appBarMaxHeight = maxHeight,
                offsetHigherBound = offsetLowerBound,
            )
        )
    }

    val snapLayout by remember(connection) {
        mutableStateOf(
            object : SnapLayoutInfoProvider {

                override fun calculateApproachOffset(velocity: Float, decayOffset: Float): Float {
                    Log.d(
                        "SnapFlingBehaviorDemo",
                        "calculateApproachOffset: $decayOffset, velocity: $velocity"
                    )
                    return decayOffset
                }

                override fun calculateSnapOffset(velocity: Float): Float {
                    val offset = if (connection.appBarOffset <= -connection.appBarMaxHeight ||
                        connection.appBarOffset >= 0
                    ) {
                        Log.d("SnapFlingBehaviorDemo: ", "else1")
                        0f
                    } else if (connection.appBarOffset.absoluteValue >= (connection.appBarMaxHeight * 0.5f)) {
                        Log.d("SnapFlingBehaviorDemo: ", "else2")
                        connection.appBarMaxHeight + connection.appBarOffset.toFloat()
                    } else {
                        Log.d(
                            "SnapFlingBehaviorDemo: ",
                            "else3: ${connection.appBarOffset.toFloat()}"
                        )
                        connection.appBarOffset.toFloat()
                    }
                    Log.d(
                        "SnapFlingBehaviorDemo: ",
                        "offset: $offset, appBarMaxHeight: ${connection.appBarMaxHeight}"
                    )
                    return offset
                }
            }
        )
    }

    val snapFlingBehavior = rememberSnapFlingBehavior(snapLayout)

    Box(
        Modifier.nestedScroll(connection)
    ) {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }

        LazyColumn(
            modifier = Modifier
                .imePadding(),
            contentPadding = PaddingValues(
                top = 8.dp + SearchBarDefaults.InputFieldHeight +
                        systemBarsTopPadding
            ),
            flingBehavior = snapFlingBehavior
        ) {
            /*item {
                Spacer(
                    Modifier
                        .windowInsetsTopHeight(
                            WindowInsets.systemBars
                        )
                )
            }*/
            items((1..100).toList()) {
                ListItem(
                    headlineContent = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Red)
                        )
                    },
                    modifier = Modifier
                        .clickable { }
                )
            }

            item {
                Spacer(
                    Modifier
                        .onSizeChanged {
                            size = it
                        }
                        .windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                )
            }
        }

        Text(
            "appBarOffset: ${connection.appBarOffset}",
            modifier = Modifier
                .align(
                    Alignment.Center
                )
        )

        var query by remember {
            mutableStateOf("")
        }

        var expanded by remember {
            mutableStateOf(false)
        }

        SearchBar(
            windowInsets = if (expanded) {
                SearchBarDefaults.windowInsets
            } else {
                WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            },
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {},
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )
            },
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            },
            modifier = Modifier
                .offset {
                    IntOffset(x = 0, y = connection.appBarOffset)
                }
//                .padding(WindowInsets.systemBars.asPaddingValues())
                .align(Alignment.TopCenter)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WindowInsetsPreview() {
    PanatilihinTheme {
        WindowInsetsDemo()
    }
}