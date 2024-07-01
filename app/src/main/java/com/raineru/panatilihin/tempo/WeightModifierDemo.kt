package com.raineru.panatilihin.tempo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.R
import com.raineru.panatilihin.ui.theme.PanatilihinTheme


@ExperimentalMaterial3Api
@Composable
fun CompactWeatherCard(
    nameOfLocation: String,
    shortDescription: String,
    @DrawableRes shortDescriptionIcon: Int,
    weatherInDegrees: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherWithDegreesSuperscript = remember(weatherInDegrees) {
        "$weatherInDegrees°"
    }
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = nameOfLocation,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                ShortWeatherDescriptionWithIconRow(
                    shortDescription = shortDescription,
                    iconRes = shortDescriptionIcon
                )
            }
            Text(
                text = weatherWithDegreesSuperscript,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
private fun ShortWeatherDescriptionWithIconRow(
    shortDescription: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = shortDescription,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CompactWeatherCardPreview() {
    PanatilihinTheme {
        CompactWeatherCard(
            nameOfLocation = "Biday, San Fernando, La Union, Lakd jdiwl lsdkjwi klsdfkj",
            shortDescription = "Light of the North",
            shortDescriptionIcon = R.drawable.ic_launcher_foreground,
            weatherInDegrees = "17",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardMinimalExample() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedCard {
            Text(text = "Hello, world!")
            Text(text = "Hello, world! 2")
            Text(text = "Hello, world! 3")
        }
    }
}