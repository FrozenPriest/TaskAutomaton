package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.program.Program

@Preview
@Composable
fun ProgramItemPreview() {
    ProgramItem(
        program = Program(1, "Name", emptyList()),
        onClick = { println("click") },
        onClickEdit = { println("edit") },
        onClickDelete = { println("delete") }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProgramItem(
    program: Program,
    onClick: () -> Unit,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = { expanded.value = true })
            .padding(bottom = 4.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 4.dp, end = 4.dp)
        ) {
            Text(
                text = program.name,
                style = MaterialTheme.typography.h5
            )
        }


        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    onClickEdit()
                    expanded.value = false
                }
            ) {
                Text("Rename")
            }
            DropdownMenuItem(
                onClick = {
                    onClickDelete()
                    expanded.value = false
                }
            ) {
                Text("Delete")
            }
        }

    }
}