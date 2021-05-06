package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varName: String) : Function("Is exist", varName, R.drawable.icon_sample) {

    override fun perform(program: Program, context: Context) {
        functionResult = program.variables.containsKey(varName)
    }
}