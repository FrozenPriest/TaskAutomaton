package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varResult: String, val varName: String): Command {
    override fun perform(program: Program, context: Context) {
        program.variables[varResult] = program.variables.containsKey(varName)
    }
}