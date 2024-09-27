@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.raineru.panatilihin.tempo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class GoogleKeepAppBar {

    @Preview(showBackground = true)
    @Composable
    fun GoogleKeepAppBarMain() {
        var showDetails by remember {
            mutableStateOf(false)
        }

        Box (Modifier.fillMaxSize()) {
            SharedTransitionLayout(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                AnimatedContent(
                    targetState = showDetails,
                    label = "GoogleKeepAppBar"
                ) {targetState ->
                    if (!targetState) {
                        Box (
                            modifier = Modifier
                                .clickable {
                                    showDetails = true
                                }
                                .width(300.dp)
                                .height(100.dp)
                                .background(Color.Gray)
                        ) {

                        }
                    } else {
                        Box (
                            modifier = Modifier
                                .clickable {
                                    showDetails = false
                                }
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Gray)
                        ) {

                        }
                    }
                }
            }
        }
    }
}