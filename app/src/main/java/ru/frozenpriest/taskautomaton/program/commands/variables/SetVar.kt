package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.commands.Function
import ru.frozenpriest.taskautomaton.program.Program

class SetVar(val varName: String, val value: Any) : Command() {
    override val commandName: String
        get() = "Set variable"
    override val commandDescription: String
        get() = "$varName = $value"
    override val iconId: Int
        get() = R.drawable.icon_sample


    override fun perform(program: Program, context: Context) {
        if (value is Function) {
            value.perform(program, context)
            program.variables[varName] = value.functionResult

        } else {
            program.variables[varName] = value
        }
    }
}