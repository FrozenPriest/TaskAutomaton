package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class CheckVar(private val varName: String): Function() {
    override val commandName: String
        get() = "Check as boolean"
    override val commandDescription: String
        get() = varName
    override val iconId: Int
        get() = R.drawable.icon_sample

    override fun perform(program: Program, context: Context) {
        val ev = ExistVar(varName)
        ev.perform(program, context)
        if(ev.functionResult) {
            val variable = program.variables[varName]
            if(variable is Boolean) {
                functionResult = variable
                return
            }
        }
    }
}