package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class IncVar(private val varName: String): Command() {
    override val commandName: String
        get() = "Increment"
    override val commandDescription: String
        get() = "$varName++"
    override val iconId: Int
        get() = R.drawable.icon_sample
    override fun perform(program: Program, context: Context) {
        var variable = program.variables[varName] as Int
        variable++
        program.variables[varName] = variable

    }
}