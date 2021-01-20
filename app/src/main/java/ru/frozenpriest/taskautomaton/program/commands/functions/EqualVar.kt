package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class EqualVar(val var1: String, val var2: String) : Function() {
    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1].toString()
        val var2Value = program.variables[var2].toString()
        functionResult = var1Value == var2Value
    }
}