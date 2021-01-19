package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class IfCondition(private val condition: Conditional) : Command() {
    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        if (!condition.conditionResult) {
            val elseIndex =
                program.commands.indices.firstOrNull { (program.commands[it].level == level) and (program.commands[it] is ElseCondition) }
            if (elseIndex != null) {
                program.commandPointer = elseIndex
            } else {
                val endIfIndex =
                    program.commands.indices.firstOrNull { (program.commands[it].level == level) and (program.commands[it] is EndIf) }
                endIfIndex?.let {
                    program.commandPointer = endIfIndex
                }
            }
            //goto ElseCondition
            //goto EndIf
        }
    }
}