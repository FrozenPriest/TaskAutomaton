package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program

class ExecuteProgram(val program: String, val args: HashMap<String, Any>) : Command(
    name = "Execute $program with args",
    description = "",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}