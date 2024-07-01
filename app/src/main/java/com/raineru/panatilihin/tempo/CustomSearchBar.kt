package com.raineru.panatilihin.tempo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onClearClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(50))

            /*.border(
                BorderStroke(
                    0.1.dp,
                    SolidColor(MaterialTheme.colorScheme.onSurface)
                ),
                RoundedCornerShape(12.dp)
            ),*/
//        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            leadingIcon = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            trailingIcon = {
                if (text.isNotBlank()) {
                    IconButton(onClick = onClearClicked) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )

                    }
                }
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
//            cursorColor = MaterialTheme.colorScheme.onBackground*/
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MySearchBarWithTextPreview(modifier: Modifier = Modifier) {
    PanatilihinTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MySearchBar(
                text = "Kaiju No. 8",
                placeholder = "Search notes",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySearchBarWithoutTextPreview(modifier: Modifier = Modifier) {
    PanatilihinTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MySearchBar(
                text = "",
                placeholder = "Search notes",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}