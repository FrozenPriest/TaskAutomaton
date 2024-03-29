package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

@JsonTypeName("EndIf")
class EndIf : Command("End if", "", R.drawable.icon_sample, CommandType.Uncategorized, CommandBuilder.CommandClass.EndIf) {

    override fun perform(program: Program, context: Context) {
        val endIfIndex =
            program.commands.indices.firstOrNull {
                (it > program.commandPointer) && (program.commands[it].info.level == info.level) &&
                        ((program.commands[it] is EndElse) || (program.commands[it] is IfCondition))
            }
        endIfIndex?.let {
            if (program.commands[endIfIndex] is EndElse)
                program.commandPointer = endIfIndex
        }
    }
}