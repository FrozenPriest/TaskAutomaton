package ru.frozenpriest.taskautomaton.presentation.components

import android.view.Gravity
import android.widget.Toast
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
import ru.frozenpriest.taskautomaton.program.commands.functions.CheckVar
import ru.frozenpriest.taskautomaton.program.commands.functions.LowerVar
import ru.frozenpriest.taskautomaton.program.commands.functions.NotFunction
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.IncVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar
import java.util.*


@Preview
@Composable
private fun CommandCardPreview() {
    val list = listOf(
        SetVar("funkyVar1", true),
        SetVar("funkyVar2", true),
        SetVar("f3", 0),
        SetVar("f4", 9),
        SetVar("f10", 10),
        VibrateWithPattern(arrayOf(200, 100, 200, 100, 400, 200, 500)),
        WhileCondition(NotFunction(LowerVar("f10", "f3"))),
        UseTts("Test is %s", arrayOf("f3"), Locale.ENGLISH),
        IncVar("f3"),
        EndWhile(),

        IfCondition(CheckVar("funkyVar1")),
        IfCondition(CheckVar("funkyVar2")),
        ShowToast(
            "Test test test",
            arrayOf(),
            Toast.LENGTH_LONG
        ),
        EndIf(),
        ElseCondition(),
        ShowToast(
            "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
            arrayOf(),
            Toast.LENGTH_LONG
        ),
        EndElse(),
        EndIf(),
        IfCondition(CheckVar("funkyVar1")),
        ShowHtml(
            "<html>\n" +
                    "<body>\n" +
                    "<h1 style=\"font-size:300%%;\">This is a heading</h1>\n" +
                    "<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\n" +
                    "</body>\n" +
                    "</html>\n",
            duration = 15000,
            args = arrayOf("funkyVar1", "funkyVar4"),
            textColor = android.graphics.Color.WHITE,
            backgroundColor = android.graphics.Color.BLACK,
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
        ),
        EndIf(),
        ElseCondition(),
        ShowToast(
            "New text %s, to go %s",
            arrayOf("funkyVar1", "funkyVar2"),
            Toast.LENGTH_LONG
        ),
        EndElse()
    )
    CommandsList(
        false,
        Program(list),
        {},
        {},
        {}
    )
}

@Composable
fun CommandsList(
    loading: Boolean,
    program: Program,
    onStartClick: () -> Unit,
    onStepClick: () -> Unit,
    onStopClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onStartClick) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
                }
                IconButton(onClick = onStepClick) {
                    Icon(imageVector = Icons.Filled.NavigateNext, contentDescription = "Step")
                }
                IconButton(onClick = onStopClick) {
                    Icon(imageVector = Icons.Filled.Stop, contentDescription = "Stop")
                }
            }
        }
    ) {
        LazyColumn {
            itemsIndexed(
                items = program.commands
            ) { index, command ->
                CommandCard(
                    command = command
                ) { println(index) }

            }
        }
    }


}
