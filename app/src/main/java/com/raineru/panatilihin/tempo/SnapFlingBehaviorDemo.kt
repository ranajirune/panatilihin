@file:OptIn(ExperimentalMaterial3Api::class)

package com.raineru.panatilihin.tempo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raineru.panatilihin.ui.CollapsingAppBarNestedScrollConnection
import kotlin.math.absoluteValue

class SnapFlingBehaviorDemo {

    @Preview
    @Composable
    fun SnapFlingBehaviorDemoPreview() {
        val state = rememberLazyListState()

        var zero by remember {
            mutableStateOf(false)
        }

        val appBarMaxHeightPx = with(LocalDensity.current) {
            val barTopPadding = 8.dp
            (SearchBarDefaults.InputFieldHeight + barTopPadding)
                .roundToPx()
        }

        val connection = remember {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }

        val density = LocalDensity.current

        val snapLayout = remember {
            object : SnapLayoutInfoProvider {

                override fun calculateApproachOffset(velocity: Float, decayOffset: Float): Float {
                    Log.d("SnapFlingBehaviorDemo", "calculateApproachOffset: $decayOffset, velocity: $velocity")
                    return decayOffset
                }

                override fun calculateSnapOffset(velocity: Float): Float {
                    Log.d("SnapFlingBehaviorDemo", "velocity: $velocity")
                    Log.d("SnapFlingBehaviorDemo", "appBarMaxHeightPx: $appBarMaxHeightPx")
                    /*return if (zero) {
                        0f
                    } else {
                        with(density) {
                            val offset = 200.dp.toPx()
                            if (velocity < 0) offset * -1.0f else offset
                        }
                    }*/
                    with(density) {
                        Log.d("SnapFlingBehaviorDemo", "appBarOffset: ${connection.appBarOffset}")
                        val offset = if (connection.appBarOffset <= -connection.appBarMaxHeight) {
                            0f
                        } else if (connection.appBarOffset.absoluteValue >= (appBarMaxHeightPx * 0.5f)) {
                            Log.d("SnapFlingBehaviorDemo: ", "else2")
                            connection.appBarMaxHeight + connection.appBarOffset.toFloat()
                        } else {
                            Log.d("SnapFlingBehaviorDemo: ", "else3")
                            connection.appBarOffset.toFloat()
                        }
                        Log.d("SnapFlingBehaviorDemo: ", "offset: $offset")
                        return offset
                    }
                }
            }
        }

        val snapFlingBehavior = rememberSnapFlingBehavior(snapLayout)

        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(connection = connection)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = state,
//            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
                flingBehavior = snapFlingBehavior
            ) {
                items(200) {
                    val background = if (it % 2 == 0) Color.Red else Color.Green
                    Box(
                        modifier =
                        Modifier
                            .height(400.dp)
                            .width(200.dp)
//                            .padding(8.dp)
                            .background(background),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(it.toString(), fontSize = 32.sp)
                    }
                }
            }

            var expanded by remember {
                mutableStateOf(false)
            }

            var query by remember {
                mutableStateOf("")
            }

            /*SearchBar(
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
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = connection.appBarOffset
                        )
                    }
            )
            {

            }*/

            Button(onClick = { zero = !zero }, modifier = Modifier.align(Alignment.BottomCenter)) {
                Text("zero: $zero")
            }

            Text("offset: ${connection.appBarOffset}", modifier = Modifier.align(Alignment.Center))
        }
    }
}