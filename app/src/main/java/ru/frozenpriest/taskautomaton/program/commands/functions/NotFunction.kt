package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

class NotFunction(val function: Function) : Function() {
    override fun perform(program: Program, context: Context) {
        function.perform(program, context)
        conditionResult = !function.conditionResult
    }
}