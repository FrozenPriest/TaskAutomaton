package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.Function
import ru.frozenpriest.taskautomaton.program.Program

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varName: String): Function() {
    override val commandName: String
        get() = "Is exist"
    override val commandDescription: String
        get() = varName
    override val iconId: Int
        get() = R.drawable.icon_sample


    override fun perform(program: Program, context: Context) {
        functionResult = program.variables.containsKey(varName)
    }
}