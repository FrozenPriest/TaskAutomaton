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
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program


@Composable
fun TriggerList(
    triggers: List<TriggerEntity>,
    programs: List<Program>,
    onNavigateToDetailsScreen: (triggerId: Long) -> Unit,
    onSetTriggersProgram: (program: Program) -> Unit,
    onAddNewTrigger: (name: String) -> Unit,
    onRenameTrigger: (trigger: TriggerEntity) -> Unit,
    onDeleteTrigger: (trigger: TriggerEntity) -> Unit
) {
    val (showAddDialog, setShowAddDialog) = remember { mutableStateOf(false) }
    val (showRenameDialog, setShowRenameDialog) = remember { mutableStateOf(false) }
    val editableTriggerEntity = remember { mutableStateOf<TriggerEntity?>(null) }
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
                items = triggers
            ) { item ->
                TriggerItem(
                    trigger = item,
                    programs = programs,
                    setProgram = { onSetTriggersProgram(it) },
                    openTrigger = { onNavigateToDetailsScreen(item.id) },
                    editTrigger = {
                        setShowRenameDialog(true)
                        editableTriggerEntity.value = item
                    },
                    deleteTrigger = { onDeleteTrigger(item) }
                )

            }
        }
        AddNewDialog(
            showDialog = showAddDialog,
            setShowDialog = setShowAddDialog,
            text = "",
            onConfirm = onAddNewTrigger
        )
        editableTriggerEntity.value?.let {
            AddNewDialog(
                showDialog = showRenameDialog,
                setShowDialog = setShowRenameDialog,
                text = it.name,
                onConfirm = { name ->
                    it.name = name
                    onRenameTrigger(it)
                }
            )
        }
    }
}