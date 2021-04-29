package ru.frozenpriest.taskautomaton.program

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector


abstract class Command : ICommand {
    var level = 0

    abstract val commandName: String
    abstract val commandDescription: String
    abstract val iconVector: ImageVector
}


interface ICommand {
    fun perform(program: Program, context: Context)
}
