package com.raineru.panatilihin.tempo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ArtistCard() {
    Row(
        modifier = Modifier
            .size(width = 400.dp, height = 400.dp)
            .background(Color.Blue)
    ) {
        Box(

            modifier = Modifier
                .requiredSize(420.dp)
                .background(Color.Green)
                .wrapContentSize(align = Alignment.TopCenter, unbounded = false)
//                .size(150.dp)

        ) {
            Text("North", modifier = Modifier.align(Alignment.TopCenter))
            Text("South", modifier = Modifier.align(Alignment.BottomCenter))
            Text("East", modifier = Modifier.align(Alignment.CenterEnd))
            Text("West", modifier = Modifier.align(Alignment.CenterStart))
        }
        Text("I'm a text")
    }
}

@Composable
fun WrapContentSizeDemo() {
    Column {
        Box(
            Modifier
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .wrapContentSize(Alignment.TopCenter)
                .size(10.dp)
                .background(Color.Blue)
        )
        Text("I'm a text")
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistCardPreview() {
    ArtistCard()
}

@Preview(showBackground = true)
@Composable
fun WrapContentSizeDemoPreview() {
    WrapContentSizeDemo()
}