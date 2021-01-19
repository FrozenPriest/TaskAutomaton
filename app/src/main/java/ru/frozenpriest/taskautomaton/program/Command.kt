package ru.frozenpriest.taskautomaton.program

import android.content.Context

abstract class Command: ICommand {
    var level = 0
}


interface ICommand {
    fun perform(program: Program, context: Context)
}
