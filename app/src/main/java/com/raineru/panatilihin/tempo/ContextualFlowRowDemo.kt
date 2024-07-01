package com.raineru.panatilihin.tempo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContextualFlowRowDemo(modifier: Modifier = Modifier) {
    /*val totalCount = 40
    var maxLines by remember {
        mutableIntStateOf(2)
    }

    val moreOrCollapseIndicator = @Composable { scope: ContextualFlowRowOverflowScope ->
        val remainingItems = totalCount - scope.shownItemCount
        FilterChip(
            onClick = {},
            label = {
                Text(text =
                if (remainingItems == 0) "Less" else "+$remainingItems", onClick = {
                    if (remainingItems == 0) {
                        maxLines = 2
                    } else {
                        maxLines += 5
                    }
                })
            }
        )
    }
    ContextualFlowRow(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxWidth(1f)
            .padding(16.dp)
            .wrapContentHeight(align = Alignment.Top)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxLines = maxLines,
        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
            minRowsToShowCollapse = 4,
            expandIndicator = moreOrCollapseIndicator,
            collapseIndicator = moreOrCollapseIndicator
        ),
        itemCount = totalCount
    ) { index ->
        ChipItem("Item $index")
    }*/
}