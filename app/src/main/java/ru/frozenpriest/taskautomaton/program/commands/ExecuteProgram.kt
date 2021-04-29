package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class ExecuteProgram(val program: String, val args: HashMap<String, Any>): Command() {
    override val commandName: String
        get() = "Execute $program with args"
    override val commandDescription: String
        get() = ""
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer

    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}