package ru.frozenpriest.taskautomaton.program

import android.content.Context

interface Command {
    fun perform(program: Program, context: Context)
}
