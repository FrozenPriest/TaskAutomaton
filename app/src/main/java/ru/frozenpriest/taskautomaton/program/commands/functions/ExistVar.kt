package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varName: String): Function() {
    override fun perform(program: Program, context: Context) {
        functionResult = program.variables.containsKey(varName)
    }
}