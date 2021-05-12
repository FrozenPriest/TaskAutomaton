package ru.frozenpriest.taskautomaton.program

import android.content.Context
import android.util.Log
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import kotlin.math.abs


class Program(val id: Long = 0, var name: String, var commands: List<Command>) {
    var listener: OnCommandRunListener? = null
    var isSyntaxValid = false

    constructor(
        id: Long = 0,
        name: String,
        commands: List<Command>,
        params: HashMap<String, Any>
    ) : this(id, name, commands) {
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
            command.info.level = level
            when (command) {
                is IfCondition -> {
                    level++
                    brackets[0]++
                }
                is EndIf -> {
                    command.info.level--
                    level--
                    brackets[0]--
                }
                is ElseCondition -> {
                    level++
                    brackets[1]++
                }
                is EndElse -> {
                    command.info.level--
                    level--
                    brackets[1]--
                }
                is WhileCondition -> {
                    level++
                    brackets[2]++
                }
                is EndWhile -> {
                    command.info.level--
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

    fun removeCommandAt(adapterPosition: Int): List<Int> {
        val mutableSet = mutableSetOf<Int>()
        val command = commands[adapterPosition]
        mutableSet.add(adapterPosition)

        if (command is IfCondition) {
            findFromIf(command, adapterPosition, mutableSet)
        }
        if (command is EndIf || command is ElseCondition || command is EndElse) {
            var index = -1
            var pos = adapterPosition
            while (index == -1) {
                if (commands[--pos].info.level != command.info.level) continue
                if (commands[pos] is IfCondition) index = pos
            }
            findFromIf(commands[index], index, mutableSet)
        }

        if (command is WhileCondition) {
            var endWhile = -1
            val level = command.info.level

            for (i in adapterPosition until commands.size) {
                val current = commands[i]
                if (current.info.level != level)
                    continue
                if (current is EndWhile) {
                    endWhile = i
                    break
                }

            }

            mutableSet.add(endWhile)
        }
        if (command is EndWhile) {
            var index = -1
            var pos = adapterPosition
            while (index == -1) {
                if (commands[--pos].info.level != command.info.level) continue
                if (commands[pos] is WhileCondition) index = pos
            }
            mutableSet.add(index)
        }

        val list = mutableSet.toMutableList()
        list.sort()

        val mutableCommands = commands.toMutableList()
        for ((correction, i) in list.withIndex()) {
            println("Command: ${commands[i]}")
            mutableCommands.removeAt(i - correction)
        }
        commands = mutableCommands
        return list
    }

    private fun findFromIf(
        command: Command,
        adapterPosition: Int,
        mutableSet: MutableSet<Int>
    ) {
        mutableSet.add(adapterPosition) //this should be on IF
        var endIfIndex = -1
        var elseIndex = -1
        var endElseIndex = -1
        val level = command.info.level
        for (i in adapterPosition until commands.size) {
            val current = commands[i]
            if (current.info.level != level)
                continue
            if (endIfIndex == -1 && current is EndIf)
                endIfIndex = i
            if (elseIndex == -1 && current is ElseCondition && abs(i - endIfIndex) == 1)
                elseIndex = i
            if (current is EndElse && elseIndex != -1)
                endElseIndex = i

            if (endElseIndex != -1 || current.info.level < level) break
        }

        mutableSet.add(endIfIndex)
        if (elseIndex != -1) {
            mutableSet.add(elseIndex)
            mutableSet.add(endElseIndex)
        }
    }
}

interface OnCommandRunListener {
    fun onCommandRun(commandPointerFrom: Int, commandPointerTo: Int)
}