package com.raineru.panatilihin.tempo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun WindowInsetsDemo() {
    LazyColumn(
//        contentPadding =
    ) {
        item {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.systemBars
                )
            )
        }
        items((1..100).toList()) {
            ListItem(
                headlineContent = {
                    Text(
                        it.toString(),
                    )
                },
                modifier = Modifier
                    .clickable { }
            )
        }
        item {
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
            )
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