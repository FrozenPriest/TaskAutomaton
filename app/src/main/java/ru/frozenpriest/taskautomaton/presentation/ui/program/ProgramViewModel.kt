package ru.frozenpriest.taskautomaton.presentation.ui.program

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

class ProgramViewModel : ViewModel() {

    val program: MutableState<Program> = mutableStateOf(Program(1, "test", emptyList()))
    val loading = mutableStateOf(false)

    init {
        loadProgram()
    }

    fun isSyntaxValid() = program.value.isSyntaxValid
    fun loadProgram() {
        viewModelScope.launch {
            loading.value = true

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
                    textColor = Color.WHITE,
                    backgroundColor = Color.BLACK,
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
            Log.e("0", "Prog size is ${list.size}")
            program.value = Program(1, "test", list)

            loading.value = false
        }
    }

    fun stop() {
        program.value.stop()
    }

    fun nextCommand(context: Context) {
        program.value.nextCommand(context)
    }

    fun executeCommands(context: Context) {
        program.value.executeCommands(context)
    }
}