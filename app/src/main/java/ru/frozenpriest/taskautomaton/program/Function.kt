package ru.frozenpriest.taskautomaton.program

import ru.frozenpriest.taskautomaton.program.Command

abstract class Function: Command() {
    var functionResult: Boolean = false
    protected set
}