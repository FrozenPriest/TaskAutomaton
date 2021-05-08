package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.frozenpriest.taskautomaton.program.Program

@Preview
@Composable
fun ProgramListPreview() {
    ProgramList(emptyList(), {})
}

@Composable
fun ProgramList(
    programs: List<Program>,
    onNavigateToDetailsScreen: (programId: Long) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.NavigateNext, contentDescription = "Step")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Stop, contentDescription = "Stop")
                }
            }
        }
    ) {
        LazyColumn {
            itemsIndexed(
                items = programs
            ) { index, program ->
                ProgramItem(program) { onNavigateToDetailsScreen(program.id) }
            }
        }
    }
}