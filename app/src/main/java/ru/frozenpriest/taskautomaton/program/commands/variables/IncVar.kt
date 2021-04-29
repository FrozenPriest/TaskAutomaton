package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class IncVar(private val varName: String): Command() {
    override val commandName: String
        get() = "Increment"
    override val commandDescription: String
        get() = "$varName++"
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer
    override fun perform(program: Program, context: Context) {
        var variable = program.variables[varName] as Int
        variable++
        program.variables[varName] = variable

    }
}