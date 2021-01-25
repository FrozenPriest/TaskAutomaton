package ru.frozenpriest.taskautomaton.program.commands.logic

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class ElseCondition: Command() {
    override val commandName: String
        get() = "Else"
    override val commandDescription: String
        get() = ""
    override val iconId: Int
        get() = R.drawable.icon_sample
    override fun perform(program: Program, context: Context) {
    }
}