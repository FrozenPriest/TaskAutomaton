package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class EndWhile : Command() {
    override val commandName: String
        get() = "End while"
    override val commandDescription: String
        get() = ""
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer
    override fun perform(program: Program, context: Context) {
        val whileIndex = program.commands.indices.lastOrNull {
            (it < program.commandPointer)
                    && (program.commands[it].level == level)
                    && (program.commands[it] is WhileCondition)
        }

        whileIndex?.let {
            program.commandPointer = whileIndex-1
        }
    }
}