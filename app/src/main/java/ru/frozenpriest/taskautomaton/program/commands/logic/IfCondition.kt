package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("IfCondition")
class IfCondition(
    @JsonProperty("condition")
    val condition: Function
) :
    Command("If", condition.name, R.drawable.icon_sample) {

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