@file:OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.raineru.panatilihin.tempo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class GoogleKeepAppBar {

    @Preview(showBackground = true)
    @Composable
    fun GoogleKeepAppBarMain() {
        var showDetails by remember {
            mutableStateOf(false)
        }

        SharedTransitionLayout {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF8D8D8F)),
            ) {
                AnimatedContent(
                    targetState = showDetails,
                    label = "GoogleKeepAppBar",
                    modifier = Modifier
//                        .background(Color.Yellow)
                        .align(Alignment.TopCenter),
                    transitionSpec = {
                        if (targetState) {
                            ContentTransform(
                                targetContentEnter = EnterTransition.None,
                                initialContentExit = ExitTransition.None,
                            sizeTransform = null,
                                targetContentZIndex = 1f
                            )
                        } else {
                            ContentTransform(
                                targetContentEnter = EnterTransition.None,
                                initialContentExit = ExitTransition.None,
                            sizeTransform = null,
                                targetContentZIndex = 1f
                            )
                        }
                    },
                    contentAlignment = Alignment.TopCenter
                ) { targetState ->
                    if (!targetState) {
                        val roundedCornerAnimationDp by transition.animateDp(
                            label = "rounded corner"
                        ) { enterExitState ->
                            when (enterExitState) {
                                EnterExitState.PreEnter -> 0.dp
                                EnterExitState.Visible -> 50.dp
                                EnterExitState.PostExit -> 0.dp
                            }
                        }

                        Box(
                            Modifier
                                .clip(RoundedCornerShape(roundedCornerAnimationDp))
                                .sharedBounds(
                                    rememberSharedContentState(
                                        key = "box",
                                    ),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    enter = EnterTransition.None,
                                    exit = ExitTransition.None,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(roundedCornerAnimationDp)
                                    ),
                                    zIndexInOverlay = 1f
                                )
                                .background(Color(0xFFEDEEF7))
//                                .background(Color.Red)
                                .width(300.dp)
                                .height(SearchBarDefaults.InputFieldHeight),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row {
                                Spacer(Modifier.width(20.dp))
                                Text(
                                    "Search your notes",
                                    modifier = Modifier
                                        .animateEnterExit(
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            label = "search your notes animation",
                                        )
                                )
                            }
                        }
                    } else {
                        val roundedCornerAnimationDp by transition.animateDp(
                            label = "rounded corner"
                        ) { enterExitState ->
                            when (enterExitState) {
                                EnterExitState.PreEnter -> 50.dp
                                EnterExitState.Visible -> 0.dp
                                EnterExitState.PostExit -> 50.dp
                            }
                        }

                        Column {
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(roundedCornerAnimationDp))
                                    .sharedBounds(
                                        rememberSharedContentState(
                                            key = "box"
                                        ),
                                        animatedVisibilityScope = this@AnimatedContent,
                                        enter = EnterTransition.None,
                                        exit = ExitTransition.None,
                                        clipInOverlayDuringTransition = OverlayClip(
                                            RoundedCornerShape(roundedCornerAnimationDp)
                                        ),
                                        resizeMode = ResizeMode.ScaleToBounds(
                                            contentScale = ContentScale.FillBounds
                                        )
                                    )
                                    .background(Color(0xFFECEDF6))
//                                .width(300.dp)
                                    .fillMaxWidth()
                                    .height(SearchBarDefaults.InputFieldHeight),
                            )
                            Box(
                                Modifier
                                    .animateEnterExit(
                                        enter = EnterTransition.None,
                                        exit = fadeOut(
                                            animationSpec = tween(durationMillis = 0)
                                        )
                                    )
                                    .fillMaxSize()
                                    .background(Color(0xFFF9F9FD))
                            ) {
                                Text("Labels")
                            }
                        }
                    }

                }

                Button(
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "showDetails: $showDetails")
                }
            }
        }
    }

    @Composable
    fun HomeScreen(
        query: String,
        onQueryChange: (String) -> Unit,
        onSearch: (String) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    expanded = expanded,
                    onExpandedChange = onExpandedChange
                )
            },
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {

        }
    }
}

