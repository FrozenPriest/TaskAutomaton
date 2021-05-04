package ru.frozenpriest.taskautomaton.program

import android.content.Context


abstract class Command : ICommand {
    var level = 0

    abstract val commandName: String
    abstract val commandDescription: String
    abstract val iconId: Int
}


interface ICommand {
    fun perform(program: Program, context: Context)
}
