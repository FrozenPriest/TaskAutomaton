package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class NotFunction(val function: Function) : Function() {
    override val commandName: String
        get() = "Not"
    override val commandDescription: String
        get() = function.commandName
    override val iconId: Int
        get() = R.drawable.icon_sample


    override fun perform(program: Program, context: Context) {
        function.perform(program, context)
        functionResult = !function.functionResult
    }
}