package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("WhileCondition")
class WhileCondition(
    @JsonProperty("condition")
    val condition: Function
) :
    Command("While", condition.info.description, R.drawable.icon_sample, CommandType.Logic, CommandBuilder.CommandClass.WhileCondition) {

    override fun perform(program: Program, context: Context) {
        condition.perform(program, context)
        println("While result: ${condition.functionResult}")
        if (!condition.functionResult) {
            val endWhileIndex = program.commands.indices.firstOrNull {
                (it > program.commandPointer)
                        && (program.commands[it].info.level == info.level)
                        && (program.commands[it] is EndWhile)
            }

            endWhileIndex?.let {
                program.commandPointer = endWhileIndex
            }
        }
    }
}