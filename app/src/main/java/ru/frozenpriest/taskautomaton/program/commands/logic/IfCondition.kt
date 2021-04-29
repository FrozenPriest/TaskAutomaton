package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class IfCondition(private val condition: Function) : Command() {
    override val commandName: String
        get() = "If"
    override val commandDescription: String
        get() = condition.commandName
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer

    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        if (!condition.functionResult) {
            val elseIndex =
                program.commands.indices.firstOrNull {
                    (it > program.commandPointer)
                            && (program.commands[it].level == level)
                            && ((program.commands[it] is ElseCondition) || (program.commands[it] is IfCondition))
                }
            if (elseIndex != null && program.commands[elseIndex] is ElseCondition) {
                program.commandPointer = elseIndex
            } else {
                val endIfIndex =
                    program.commands.indices.firstOrNull {
                        (it > program.commandPointer)
                                && (program.commands[it].level == level)
                                && (program.commands[it] is EndIf)
                    }
                endIfIndex?.let {
                    program.commandPointer = endIfIndex
                }
            }
            //goto ElseCondition
            //goto EndIf
        }
    }
}