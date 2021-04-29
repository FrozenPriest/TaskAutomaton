package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class SetVar(val varName: String, val value: Any) : Command() {
    override val commandName: String
        get() = "Set variable"
    override val commandDescription: String
        get() = "$varName = $value"
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer


    override fun perform(program: Program, context: Context) {
        if (value is Function) {
            value.perform(program, context)
            program.variables[varName] = value.functionResult

        } else {
            program.variables[varName] = value
        }
    }
}