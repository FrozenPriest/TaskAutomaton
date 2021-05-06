package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

class NotFunction(val function: Function) :
    Function("Not", function.name, R.drawable.icon_sample) {

    override fun perform(program: Program, context: Context) {
        function.perform(program, context)
        functionResult = !function.functionResult
    }
}