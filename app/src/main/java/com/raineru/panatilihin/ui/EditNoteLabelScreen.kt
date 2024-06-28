package com.raineru.panatilihin.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raineru.panatilihin.data.Label
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun EditNoteLabelScreen(
    labels: List<Label>,
    query: String,
    onUpdateLabel: (Long, Boolean) -> Unit,
    onUpdateQuery: (String) -> Unit,
    onCreateNewLabel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (
            labels.count { it.name.contentEquals(query.trim(), ignoreCase = true) } == 0 &&
            query.isNotBlank()
        ) {
            item(key = 0) {
                CreateNewLabel(
                    text = query.trim(),
                    onClick = {
                        onCreateNewLabel(it)
                        onUpdateQuery("")
                    }
                )
            }
        }

        labels
            .filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            .forEach { label ->
            item(key = label.id) {
                ToggleableLabel(
                    text = label.name,
                    onCheckedChange = {
                        onUpdateLabel(label.id, it)
                    }
                )
            }
        }
    }
}

@Composable
fun ToggleableLabel(
    text: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .clickable {
                checked = !checked
                onCheckedChange.invoke(checked)
            }
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.MailOutline, contentDescription = null)
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange.invoke(it)
            },
        )
    }
}

@Composable
fun CreateNewLabel(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onClick(text)
            }
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = "Create \"$text\"",
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ToggleableLabelPreview() {
    PanatilihinTheme {
        val text by rememberSaveable {
            mutableStateOf("A New Label")
        }
        ToggleableLabel(
            text = text,
            onCheckedChange = {

            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun EditNoteLabelScreenPreview() {
    PanatilihinTheme {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        val list = remember {
            mutableStateListOf(
                Label(name = "Bulbasaur", id = 1L),
                Label(name = "Ivysaur", id = 2L),
                Label(name = "Venusaur", id = 3L),
                Label(name = "Charmander", id = 4L),
                Label(name = "Charmeleon", id = 5L),
                Label(name = "Charizard", id = 6L),
                Label(name = "Squirtle", id = 7L),
                Label(name = "Wartortle", id = 8L),
                Label(name = "Blastoise", id = 9L),
            )
        }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            active = true,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            placeholder = {
                Text("Enter label name")
            }
        ) {
            EditNoteLabelScreen(
                labels = list,
                onUpdateLabel = { id: Long, checked: Boolean ->
                    Log.d("EditNoteLabelScreen", "onUpdateLabel: $id, $checked")
                },
                query = query,
                onUpdateQuery = {
                    query = it
                },
                onCreateNewLabel = {
                    list.add(0, Label(name = it, id = System.currentTimeMillis()))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateNewLabelPreview() {
    PanatilihinTheme {
        CreateNewLabel(text = "Label S", onClick = {})
    }
}