package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.program.Program

@Preview
@Composable
fun ProgramItemPreview() {
    ProgramItem(
        program = Program(1, "Name", emptyList()),
        onClick = {}
    )
}


@Composable
fun ProgramItem(
    program: Program,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(bottom = 4.dp),
        elevation = 8.dp,
    ) {
        Column(

        ) {
            Text(
                text = program.name,
                style = MaterialTheme.typography.h5
            )
        }


    }
}