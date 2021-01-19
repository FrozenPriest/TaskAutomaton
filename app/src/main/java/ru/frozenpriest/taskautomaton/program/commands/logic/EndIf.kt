package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class EndIf: Command() {
    override fun perform(program: Program, context: Context) {
        val endIfIndex =
            program.commands.indices.firstOrNull { (program.commands[it].level == level) and (program.commands[it] is EndElse) }
        endIfIndex?.let {
            program.commandPointer = endIfIndex
        }
    }
}