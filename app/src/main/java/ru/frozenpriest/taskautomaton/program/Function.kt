package ru.frozenpriest.taskautomaton.program

abstract class Function : Command() {
    var functionResult: Boolean = false
        protected set
}