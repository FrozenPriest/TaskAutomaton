package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class EndWhile : Command() {
    override val commandName: String
        get() = "End while"
    override val commandDescription: String
        get() = ""
    override val iconId: Int
        get() = R.drawable.icon_sample
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