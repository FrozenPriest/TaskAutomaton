package ru.frozenpriest.taskautomaton.program

import android.content.Context
import ru.frozenpriest.taskautomaton.program.commands.logic.*

class Program(var commands: List<Command>) {
    var isSyntaxGood = false

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
        //todo check valid
        var level = 0
        for(command in commands) {
            command.level = level
            if((command is IfCondition) || (command is ElseCondition) || (command is WhileCondition)) {
                level++
            }
            if((command is EndIf) || (command is EndElse) || (command is EndWhile)) {
                command.level--
                level--
            }
        }
    }
}