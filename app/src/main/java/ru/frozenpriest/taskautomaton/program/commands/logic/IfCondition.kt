package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program
import kotlin.math.max

class IfCondition(private val condition: Conditional) : Command() {
    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        if (!condition.conditionResult) {
            val elseIndex =
                program.commands.indices.firstOrNull {(it > program.commandPointer) and (program.commands[it].level == level) and  ((program.commands[it] is ElseCondition) or (program.commands[it] is IfCondition)) }
            if (elseIndex != null && program.commands[elseIndex] is ElseCondition) {
                program.commandPointer = elseIndex
            } else {
                val endIfIndex =
                    program.commands.indices.firstOrNull {(it > program.commandPointer) and (program.commands[it].level == level) and (program.commands[it] is EndIf) }
                endIfIndex?.let {
                    program.commandPointer = endIfIndex
                }
            }
            //goto ElseCondition
            //goto EndIf
        }
    }
}