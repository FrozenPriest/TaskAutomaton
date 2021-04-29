package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class GreaterVar(val var1: String, val var2: String) : Function() {
    override val commandName: String
        get() = "Check greater"
    override val commandDescription: String
        get() = "$var1 > $var2"
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer

    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1] as Number
        val var2Value = program.variables[var2] as Number
        functionResult = var1Value.toDouble() > var2Value.toDouble()
    }
}