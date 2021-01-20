package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class SetVar(val varName: String, val value: Any) : Command() {
    override fun perform(program: Program, context: Context) {
        if (value is Function) {
            value.perform(program, context)
            program.variables[varName] = value.conditionResult

        } else {
            program.variables[varName] = value
        }
    }
}