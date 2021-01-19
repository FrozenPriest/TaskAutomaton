package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varName: String): Conditional() {
    override fun perform(program: Program, context: Context) {
        conditionResult = program.variables.containsKey(varName)
    }
}