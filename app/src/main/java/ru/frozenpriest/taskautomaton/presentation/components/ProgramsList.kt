package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.frozenpriest.taskautomaton.program.Program

@Preview
@Composable
fun ProgramListPreview() {
    ProgramList(listOf(Program(0, "name", emptyList())), {}, {}, {}) {}
}

@Composable
fun ProgramList(
    programs: List<Program>,
    onNavigateToDetailsScreen: (programId: Long) -> Unit,
    onAddNewProgram: (name: String) -> Unit,
    onRenameProgram: (program: Program) -> Unit,
    onDeleteProgram: (program: Program) -> Unit
) {
    val (showAddDialog, setShowAddDialog) = remember { mutableStateOf(false) }
    val (showRenameDialog, setShowRenameDialog) = remember { mutableStateOf(false) }
    val editableProgram = remember { mutableStateOf<Program?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        setShowAddDialog(true)
                    }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            }
        }
    ) {
        LazyColumn {
            items(
                items = programs
            ) { item ->
                ProgramItem(
                    program = item,
                    onClick = { onNavigateToDetailsScreen(item.id) },
                    onClickEdit = {
                        setShowRenameDialog(true)
                        editableProgram.value = item
                    },
                    onClickDelete = { onDeleteProgram(item) }
                )
            }
        }
        AddNewProgramDialog(
            showDialog = showAddDialog,
            setShowDialog = setShowAddDialog,
            text = "",
            onConfirm = onAddNewProgram
        )
        editableProgram.value?.let {
            AddNewProgramDialog(
                showDialog = showRenameDialog,
                setShowDialog = setShowRenameDialog,
                text = it.name,
                onConfirm = { name ->
                    it.name = name
                    onRenameProgram(it)
                }
            )
        }
    }
}