package ru.frozenpriest.taskautomaton.program

import android.content.Context

class ExecuteProgram(val program: String, val args: HashMap<String, Any>): Command {
    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}