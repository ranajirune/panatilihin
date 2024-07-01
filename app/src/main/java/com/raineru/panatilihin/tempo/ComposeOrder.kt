package com.raineru.panatilihin.tempo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AComposeUi(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }

    /*OutlinedCard(
//        onClick = { selected = !selected },
        border = if (selected) {
            BorderStroke(3.dp, SolidColor(Color(0xFF116682)))
        } else {
            BorderStroke(Dp.Hairline, SolidColor(Color(0xFFC0C8CD)))
        },
//        border = BorderStroke(3.dp, SolidColor(Color(0xFFC0C8CD))),
        modifier = Modifier
            .padding(8.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { selected = !selected }
            ),

        ) {*/
    Column(
        modifier = Modifier
            .border(
                border = if (selected) {
                    BorderStroke(
                        3.dp,
                        SolidColor(Color(0xFF116682))
                    )
                } else {
                    BorderStroke(
                        1.dp,
                        SolidColor(Color(0xFFC0C8CD))
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = {},
                onLongClick = { selected = !selected }
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome!")
        Text("It's good to see you, Kodee!")
        Text("Hope you're enjoying Compose...")
        Text("Have a nice day!")
    }
//    }
}

@Preview(showBackground = true)
@Composable
fun AComposeUiPreview() {
    PanatilihinTheme {
        Row(
            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
        ) {
            AComposeUi(
                modifier = Modifier
                    .clickable {}
//                    .border(4.dp, Color.Magenta)
            )
        }
    }
}