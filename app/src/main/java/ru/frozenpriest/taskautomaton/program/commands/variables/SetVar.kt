package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.Function

class SetVar(val varName: String, val value: Any) : Command(
    name = "Set variable",
    description = "$varName = $value",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        if (value is Function) {
            value.perform(program, context)
            program.variables[varName] = value.functionResult

        } else {
            program.variables[varName] = value
        }
    }
}