package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program
import kotlin.math.max

class EndIf : Command() {
    override fun perform(program: Program, context: Context) {
        val endIfIndex =
            program.commands.indices.firstOrNull {(it > program.commandPointer) && (program.commands[it].level == level) && ((program.commands[it] is EndElse) || (program.commands[it] is IfCondition)) }
        endIfIndex?.let {
            if (program.commands[endIfIndex] is EndElse)
                program.commandPointer = endIfIndex
        }
    }
}