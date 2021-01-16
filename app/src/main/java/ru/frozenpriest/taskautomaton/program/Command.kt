package ru.frozenpriest.taskautomaton.program

interface Command {
    fun perform(program: Program)
}
