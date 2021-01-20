package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class LowerVar(val var1: String, val var2: String) : Conditional() {
    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1] as Number
        val var2Value = program.variables[var2] as Number
        conditionResult = var1Value.toDouble() < var2Value.toDouble()
    }
}