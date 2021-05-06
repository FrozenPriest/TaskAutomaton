package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

@JsonTypeName("EndElse")
class EndElse : Command("End else", "", R.drawable.icon_sample) {

    override fun perform(program: Program, context: Context) {
    }
}