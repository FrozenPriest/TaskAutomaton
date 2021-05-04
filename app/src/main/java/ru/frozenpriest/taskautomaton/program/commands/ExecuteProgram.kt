package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class ExecuteProgram(val program: String, val args: HashMap<String, Any>): Command() {
    override val commandName: String
        get() = "Execute $program with args"
    override val commandDescription: String
        get() = ""
    override val iconId: Int
        get() = R.drawable.icon_sample

    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}