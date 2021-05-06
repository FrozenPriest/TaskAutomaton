package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.Function
import ru.frozenpriest.taskautomaton.program.Program

class LowerVar(val var1: String, val var2: String) : Function() {
    override val commandName: String
        get() = "Check lower"
    override val commandDescription: String
        get() = "$var1 < $var2"
    override val iconId: Int
        get() = R.drawable.icon_sample

    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1] as Number
        val var2Value = program.variables[var2] as Number
        functionResult = var1Value.toDouble() < var2Value.toDouble()
    }
}