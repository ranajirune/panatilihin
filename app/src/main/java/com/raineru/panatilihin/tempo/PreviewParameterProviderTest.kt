package com.raineru.panatilihin.tempo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

class StringPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "String 1",
        "String 2",
        "String 3",
        "String 4",
        "String 5",
    )
}

@Composable
fun StringEntry(
    aString: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            aString,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StringEntryPreview(
    @PreviewParameter(StringPreviewParameterProvider::class)
    aString: String
) {
    PanatilihinTheme {
        StringEntry(aString = aString)
    }
}

@Composable
fun RedditUpvoteDownvoteChip(
    upvoteCount: Int,
    modifier: Modifier = Modifier,
    onUpvoteClick: () -> Unit = {},
    onDownvoteClick: () -> Unit = {}
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.clickable { onUpvoteClick() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Upvote",
//                modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    "$upvoteCount",
                    modifier = Modifier.padding(start = 4.dp, end = 12.dp)
                )
            }
            VerticalDivider(modifier = Modifier.height(12.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Downvote",
                modifier = Modifier
//                    .padding(horizontal = 4.dp)
                    .clickable { onDownvoteClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RedditUpvoteDownvoteChipPreview() {
    PanatilihinTheme {
        var upvoteCount by remember { mutableIntStateOf(1) }
        RedditUpvoteDownvoteChip(
            upvoteCount = upvoteCount,
            onUpvoteClick = { upvoteCount++ },
            onDownvoteClick = {
                upvoteCount--
            }
        )
    }
}