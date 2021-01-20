package ru.frozenpriest.taskautomaton.program.commands.conditionals

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class EqualVar(val var1: String, val var2: String) : Conditional() {
    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1].toString()
        val var2Value = program.variables[var2].toString()
        conditionResult = var1Value == var2Value
    }
}