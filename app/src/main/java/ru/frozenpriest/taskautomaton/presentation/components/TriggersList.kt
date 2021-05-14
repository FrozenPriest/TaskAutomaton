package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.triggers.Trigger


@Composable
fun TriggerList(
    triggers: List<TriggerEntity>,
    programs: List<Program>,
    onNavigateToDetailsScreen: (trigger: TriggerEntity) -> Unit,
    onSetTriggersProgram: (trigger: TriggerEntity, program: Program) -> Unit,
    onAddNewTrigger: (name: String, trigger: Trigger) -> Unit,
    onRenameTrigger: (trigger: TriggerEntity) -> Unit,
    onDeleteTrigger: (trigger: TriggerEntity) -> Unit
) {
    val (showAddDialog, setShowAddDialog) = remember { mutableStateOf(false) }
    val (showRenameDialog, setShowRenameDialog) = remember { mutableStateOf(false) }
    val editableTriggerEntity = remember { mutableStateOf<TriggerEntity?>(null) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        LazyColumn(
        ) {
            items(
                items = triggers
            ) { item ->
                TriggerItem(
                    trigger = item,
                    connectedProgram = programs.find{it.id == item.connectedProgramId},
                    programs = programs,
                    setProgram = { onSetTriggersProgram(item, it) },
                    openTrigger = { onNavigateToDetailsScreen(item) },
                    updateTrigger = {onRenameTrigger(item)},
                    openRenameTrigger = {
                        setShowRenameDialog(true)
                        editableTriggerEntity.value = item
                    },
                    deleteTrigger = { onDeleteTrigger(item) }
                )

            }
            item { Spacer(modifier = Modifier.width(200.dp)) }
        }
        AddNewDialog(
            showDialog = showAddDialog,
            setShowDialog = setShowAddDialog,
            text = "",
            onConfirm = { /*onAddNewTrigger(it, ) todo add trigger*/ }
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