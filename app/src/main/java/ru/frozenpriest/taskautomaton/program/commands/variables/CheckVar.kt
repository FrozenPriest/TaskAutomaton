package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class CheckVar(private val varNmae: String): Conditional() {
    override fun perform(program: Program, context: Context) {
        val ev = ExistVar(varNmae)
        ev.perform(program, context)
        if(ev.conditionResult) {
            val variable = program.variables[varNmae]
            if(variable is Boolean) {
                conditionResult = variable
                return
            }
        }
    }
}