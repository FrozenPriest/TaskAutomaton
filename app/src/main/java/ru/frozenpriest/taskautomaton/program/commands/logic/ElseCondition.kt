package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

@JsonTypeName("ElseCondition")
class ElseCondition : Command("Else", "", R.drawable.icon_sample, CommandType.Uncategorized) {

    override fun perform(program: Program, context: Context) {
    }
}