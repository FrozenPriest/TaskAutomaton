package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

@JsonTypeName("ElseCondition")
class ElseCondition : Command("Else", "", R.drawable.icon_sample) {

    override fun perform(program: Program, context: Context) {
    }
}