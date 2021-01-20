package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class WhileCondition(private val condition: Conditional) : Command() {
    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        if (!condition.conditionResult) {
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