package ru.frozenpriest.taskautomaton.program

import android.content.Context
import ru.frozenpriest.taskautomaton.program.commands.logic.ElseCondition
import ru.frozenpriest.taskautomaton.program.commands.logic.EndElse
import ru.frozenpriest.taskautomaton.program.commands.logic.EndIf
import ru.frozenpriest.taskautomaton.program.commands.logic.IfCondition

class Program(val commands: List<Command>) {

    constructor(commands: ArrayList<Command>, params: HashMap<String, Any>) : this(commands) {
        this.variables.putAll(params)
    }
    init {
        setLevels()
    }

    val variables = HashMap<String, Any>()

    var commandPointer: Int = 0

    fun executeCommands(context: Context) {
        commandPointer = 0
        while (commandPointer < commands.size) {
            println("Executing command ${commands[commandPointer]::class.simpleName}")
            commands[commandPointer].perform(this, context)
            commandPointer++
        }
    }

    fun setLevels() {
        var level = 0
        for(command in commands) {
            command.level = level
            if((command is IfCondition) or (command is ElseCondition)) level++
            if((command is EndIf) or (command is EndElse)) {
                command.level--
                level--
            }

        }
    }
}