package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun IconButtonAndIconSizeDifferent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }
        }
        Row {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
            ) {
                Icon(
                    Icons.Filled.Favorite, contentDescription = "Favorite",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ZaPreview(modifier: Modifier = Modifier) {
    PanatilihinTheme {
        IconButtonAndIconSizeDifferent()
    }
}