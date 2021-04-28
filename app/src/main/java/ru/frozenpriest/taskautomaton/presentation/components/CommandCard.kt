package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.commands.variables.AddVar


@Preview
@Composable
private fun CommandCardPreview() {
    CommandCard(
        AddVar("var1", "var2", "var3")
    ) { println("OnClick") }
}

@Composable
fun CommandCard(
    command: Command,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick }
            .padding(bottom = 4.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
        ) {

            Image(
                imageVector = Icons.Default.ImportContacts,
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .align(CenterVertically)
                    .padding(end = 8.dp)
            )
            Column(

            ) {
                Text(
                    text = command.commandName,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = command.commandDescription,
                    style = MaterialTheme.typography.h6
                )
            }
        }

    }
}
