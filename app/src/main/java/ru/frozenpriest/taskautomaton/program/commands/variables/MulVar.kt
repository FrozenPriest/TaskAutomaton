package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

/**
 * Put varName1 * varName2 into varRes
 */
class MulVar(
    private val varRes: String,
    private val varName1: String,
    private val varName2: String
) : Command() {
    override fun perform(program: Program, context: Context) {

        val a = program.variables[varName1]
        val b = program.variables[varName2]
        if(a is Number && b is Number) {
            if (a is Double || b is Double || a is Float || b is Float) {
                program.variables[varRes] = (a.toDouble() * b.toDouble())
            } else {
                program.variables[varRes] = (a.toLong() * b.toLong())
            }
        }

    }
}