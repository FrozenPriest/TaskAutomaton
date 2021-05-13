package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.service.LocationState
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.LocationTriggerType
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import java.time.DayOfWeek
import kotlin.random.Random

@Preview
@Composable
fun TriggerItemPreview() {
    val random = remember {
        Random.Default
    }
    TriggerItem(
        trigger = TriggerEntity(
            name = "Name ${random.nextInt()}", //lul
            connectedProgramId = 1,
            trigger = if (random.nextBoolean())
                LocationTrigger(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble(100.0),
                    LocationState.Undefined,
                    LocationTriggerType.values()[random.nextInt(3)]
                )
            else
                TimeTrigger(
                    random.nextInt(24),
                    random.nextInt(60),
                    listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)
                ),
            enabled = random.nextBoolean()
        ),
        programs = emptyList(),
        setProgram = {},
        openTrigger = { println("click") },
        updateTrigger = {},
        openRenameTrigger = { println("edit") },
        deleteTrigger = { println("delete") }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TriggerItem(
    trigger: TriggerEntity,
    programs: List<Program>,
    setProgram: (Program) -> Unit,
    openTrigger: () -> Unit,
    updateTrigger: () -> Unit,
    openRenameTrigger: () -> Unit,
    deleteTrigger: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val enabled = remember { mutableStateOf(trigger.enabled) }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = openTrigger, onLongClick = { expanded.value = true })
            .padding(bottom = 4.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = trigger.name,
                    style = MaterialTheme.typography.h5
                )
                RadioButton(
                    selected = enabled.value,
                    onClick = {
                        enabled.value = !enabled.value
                        trigger.enabled = !trigger.enabled
                        updateTrigger()
                    })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Selector(
                    possibleValues = programs,
                    showAsString = { it.name },
                    onItemSelected = {
                        setProgram(it)
                    }
                )
            }
        }


        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    openRenameTrigger()
                    expanded.value = false
                }
            ) {
                Text("Rename")
            }
            DropdownMenuItem(
                onClick = {
                    deleteTrigger()
                    expanded.value = false
                }
            ) {
                Text("Delete")
            }
        }

    }
}