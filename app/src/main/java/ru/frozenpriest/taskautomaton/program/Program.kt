package ru.frozenpriest.taskautomaton.program

import android.content.Context
import ru.frozenpriest.taskautomaton.MainActivity

class Program(val commands: List<Command>) {

    constructor(commands: ArrayList<Command>, params: HashMap<String, Any>) : this(commands) {
        this.variables.putAll(params)
    }
    val variables = HashMap<String, Any>()


    val commandPointer: Int = 0

    fun executeCommands(context: Context) {
        for (commandPointer in commands.indices) {
            commands[commandPointer].perform(this, context)
        }
    }
}