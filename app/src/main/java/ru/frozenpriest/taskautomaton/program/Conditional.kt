package ru.frozenpriest.taskautomaton.program

import ru.frozenpriest.taskautomaton.program.Command

abstract class Conditional: Command() {
    var conditionResult: Boolean = false
    protected set
}