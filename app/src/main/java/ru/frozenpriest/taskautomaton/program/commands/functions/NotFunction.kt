package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class NotFunction(val function: Function) : Function() {
    override val commandName: String
        get() = "Not"
    override val commandDescription: String
        get() = function.commandName
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer


    override fun perform(program: Program, context: Context) {
        function.perform(program, context)
        functionResult = !function.functionResult
    }
}