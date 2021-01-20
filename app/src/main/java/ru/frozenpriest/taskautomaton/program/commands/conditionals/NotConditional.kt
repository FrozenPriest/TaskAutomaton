package ru.frozenpriest.taskautomaton.program.commands.conditionals

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Conditional
import ru.frozenpriest.taskautomaton.program.Program

class NotConditional(val conditional: Conditional) : Conditional() {
    override fun perform(program: Program, context: Context) {
        conditional.perform(program, context)
        conditionResult = !conditional.conditionResult
    }
}