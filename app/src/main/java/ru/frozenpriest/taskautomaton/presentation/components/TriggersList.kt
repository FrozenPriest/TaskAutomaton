package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.service.LocationState
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import ru.frozenpriest.taskautomaton.program.triggers.Trigger


@ExperimentalFoundationApi
@Composable
fun TriggerList(
    triggers: List<TriggerEntity>,
    programs: List<Program>,
    onNavigateToDetailsScreen: (trigger: TriggerEntity) -> Unit,
    onSetTriggersProgram: (trigger: TriggerEntity, program: Program) -> Unit,
    onAddNewTrigger: (trigger: TriggerEntity) -> Unit,
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
                    connectedProgram = programs.find { it.id == item.connectedProgramId },
                    programs = programs,
                    setProgram = { onSetTriggersProgram(item, it) },
                    openTrigger = { onNavigateToDetailsScreen(item) },
                    updateTrigger = { onRenameTrigger(item) },
                    openRenameTrigger = {
                        setShowRenameDialog(true)
                        editableTriggerEntity.value = item
                    },
                    deleteTrigger = { onDeleteTrigger(item) }
                )

            }
            item { Spacer(modifier = Modifier.width(200.dp)) }
        }
        TriggerSelector(
            showDialog = showAddDialog,
            setShowDialog = setShowAddDialog,
            onAddNewTrigger = { onAddNewTrigger(it) }
        )
//        AddNewDialog(
//
//            text = "",
//            onConfirm = {
//                //onAddNewTrigger(it, )
//            }
//        )
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

@ExperimentalFoundationApi
@Composable
fun TriggerSelector(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onAddNewTrigger: (trigger: TriggerEntity) -> Unit
) {
    val selectedTrigger = remember {
        mutableStateOf<TriggerEntity?>(null)
    }
    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            Card(modifier = Modifier.fillMaxWidth()) {
                LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                    items(items = Trigger::class.sealedSubclasses) { item ->
                        when (item) {
                            LocationTrigger::class -> GridElement(
                                icon = Icons.Filled.Map,
                                title = "Location",
                                onSelect = {
                                    selectedTrigger.value = TriggerEntity(
                                        name = "",
                                        enabled = false,
                                        trigger = LocationTrigger(
                                            0.0,
                                            0.0,
                                            0.0,
                                            LocationState.Undefined,
                                            LocationTrigger.Type.Enter
                                        )
                                    )
                                })
                            TimeTrigger::class -> GridElement(
                                icon = Icons.Filled.Timer,
                                title = "Time",
                                onSelect = {
                                    selectedTrigger.value = TriggerEntity(
                                        name = "",
                                        enabled = false,
                                        trigger = TimeTrigger(
                                            10,
                                            0,
                                            mutableSetOf()
                                        )
                                    )
                                })
                            else -> throw NotImplementedError("Not implemented")
                        }

                    }
                }
                selectedTrigger.value?.let {
                    TriggerBuilder(
                        trigger = it,
                        build = {
                            onAddNewTrigger(it)
                            setShowDialog(false)
                            selectedTrigger.value = null
                        },
                        cancel = {
                            setShowDialog(false)
                            selectedTrigger.value = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GridElement(
    icon: ImageVector,
    title: String,
    onSelect: () -> Unit
) {
    IconButton(onClick = {
        onSelect()
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = "icon")
            Text(text = title)
        }
    }
}