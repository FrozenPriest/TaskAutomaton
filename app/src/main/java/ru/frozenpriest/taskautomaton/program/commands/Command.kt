package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Program


abstract class Command(val name: String, val description: String, val iconId: Int) : ICommand {
    var level = 0
}


interface ICommand {
    fun perform(program: Program, context: Context)
}


