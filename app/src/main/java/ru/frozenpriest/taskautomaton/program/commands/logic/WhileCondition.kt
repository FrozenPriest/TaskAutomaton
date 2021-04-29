package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class WhileCondition(private val condition: Function) : Command() {
    override val commandName: String
        get() = "While"
    override val commandDescription: String
        get() = condition.commandName
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer

    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        if (!condition.functionResult) {
            val endWhileIndex = program.commands.indices.firstOrNull {
                (it > program.commandPointer)
                        && (program.commands[it].level == level)
                        && (program.commands[it] is EndWhile)
            }

            endWhileIndex?.let {
                program.commandPointer = endWhileIndex
            }
        }
    }
}