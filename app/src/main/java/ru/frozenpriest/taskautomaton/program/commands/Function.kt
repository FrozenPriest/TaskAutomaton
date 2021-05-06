package ru.frozenpriest.taskautomaton.program.commands

import ru.frozenpriest.taskautomaton.program.Command

abstract class Function : Command() {
    var functionResult: Boolean = false
        protected set
}