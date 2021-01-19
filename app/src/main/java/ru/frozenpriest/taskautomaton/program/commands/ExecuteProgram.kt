package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class ExecuteProgram(val program: String, val args: HashMap<String, Any>): Command() {
    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}