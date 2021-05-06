package ru.frozenpriest.taskautomaton.program

import android.content.Context
import android.util.Log
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.logic.*

class Program(var commands: List<Command>) {
    var listener: OnCommandRunListener? = null
    var isSyntaxValid = false

    constructor(commands: ArrayList<Command>, params: HashMap<String, Any>) : this(commands) {
        this.variables.putAll(params)
    }

    init {
        setLevels()
    }

    val variables = HashMap<String, Any>()

    var commandPointer: Int = 0
    var running = false

    fun executeCommands(context: Context) {
        commandPointer = 0
        running = true
        while (commandPointer < commands.size && running) {
            val prevCommandPointer = commandPointer
            // println("Executing command ${commands[commandPointer]::class.simpleName}")
            commands[commandPointer].perform(this, context)
            commandPointer++
            listener?.onCommandRun(prevCommandPointer, commandPointer)
        }
    }


    fun stop() {
        running = false
        listener?.onCommandRun(commandPointer, 0)
        commandPointer = 0
        variables.clear()

    }

    fun nextCommand(context: Context) {
        if (commandPointer < commands.size) {
            val prevCommandPointer = commandPointer
            commands[commandPointer].perform(this, context)
            commandPointer++
            listener?.onCommandRun(prevCommandPointer, commandPointer)
        }
    }

    fun setLevels() {
        val brackets = Array(3) { 0 }
        var level = 0
        for (command in commands) {
            command.level = level
            when (command) {
                is IfCondition -> {
                    level++
                    brackets[0]++
                }
                is EndIf -> {
                    command.level--
                    level--
                    brackets[0]--
                }
                is ElseCondition -> {
                    level++
                    brackets[1]++
                }
                is EndElse -> {
                    command.level--
                    level--
                    brackets[1]--
                }
                is WhileCondition -> {
                    level++
                    brackets[2]++
                }
                is EndWhile -> {
                    command.level--
                    level--
                    brackets[2]--
                }
            }
            for (value in brackets) {
                if (value < 0) {
                    isSyntaxValid = false
                    Log.e("EEEE", "value = $value at ${brackets.indexOf(value)}")
                    return
                }
            }
        }
        for (value in brackets) {
            if (value != 0) {
                isSyntaxValid = false
                Log.e("EEEE", "value = $value at ${brackets.indexOf(value)}")
                return
            }
        }
        isSyntaxValid = true
    }
}

interface OnCommandRunListener {
    fun onCommandRun(commandPointerFrom: Int, commandPointerTo: Int)
}