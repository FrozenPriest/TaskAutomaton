package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SelectorPreview() {
    Selector(
        possibleValues = listOf("Item1", "Item2", "Item3"),
        showAsString = { it },
        onItemSelected = {})
}

@Composable
fun <T> Selector(
    modifier: Modifier = Modifier,
    currentValue: String = "",
    possibleValues: List<T>,
    showAsString: (T) -> String,
    onItemSelected: (item: T) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val (selectedText, setText) = remember { mutableStateOf(currentValue) }

    val icon = if (expanded.value)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown


    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { expanded.value = !expanded.value }
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedText,
                modifier = Modifier.padding(end = 4.dp),
            )
            Icon(
                icon,
                "Dropdown arrow",
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            //todo check in next composable version for lazyColumn/scrollable
            Column(
                modifier = Modifier
            ) {
                possibleValues.forEach { item ->
                    DropdownMenuItem(onClick = {
                        setText(showAsString(item))
                        onItemSelected(item)
                        expanded.value = false
                    }) {
                        Text(text = showAsString(item))
                    }
                }
            }
        }
    }
}