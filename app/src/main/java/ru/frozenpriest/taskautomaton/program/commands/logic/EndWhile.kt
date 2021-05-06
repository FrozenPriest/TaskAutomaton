package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

@JsonTypeName("EndWhile")
class EndWhile : Command("End while", "", R.drawable.icon_sample) {

    override fun perform(program: Program, context: Context) {
        val whileIndex = program.commands.indices.lastOrNull {
            (it < program.commandPointer)
                    && (program.commands[it].level == level)
                    && (program.commands[it] is WhileCondition)
        }

        whileIndex?.let {
            program.commandPointer = whileIndex - 1
        }
    }
}