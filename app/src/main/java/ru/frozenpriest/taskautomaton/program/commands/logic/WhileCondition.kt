package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.Function

class WhileCondition(private val condition: Function) :
    Command("While", condition.name, R.drawable.icon_sample) {

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