package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class CollapsingAppBarNestedScrollConnection(
    val appBarMaxHeight: Int
) : NestedScrollConnection {

    var appBarOffset: Int by mutableIntStateOf(0)
        private set

    private val settledOffsetThreshold = 10

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.roundToInt()
        val newOffset = appBarOffset + delta
        appBarOffset = newOffset.coerceIn(-appBarMaxHeight, 0)
        Log.d("CollapsingAppBarNestedScrollConnection", "available: $available, source: $source, appBarOffset: $appBarOffset")
        return Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        Log.d(
            "CollapsingAppBarNestedScrollConnection",
            "onPostScroll: source: $source, consumed: $consumed, available: $available"
        )

        if (source == NestedScrollSource.SideEffect) {
            // not lowerbound e.g (-222 instead of -224)
            if ((appBarMaxHeight + appBarOffset) <= settledOffsetThreshold) {
                appBarOffset = -appBarMaxHeight
            }

            if (appBarOffset.absoluteValue <= settledOffsetThreshold) {
                appBarOffset = 0
            }
        }

        return super.onPostScroll(consumed, available, source)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {


        return super.onPostFling(consumed, available)
    }
}
