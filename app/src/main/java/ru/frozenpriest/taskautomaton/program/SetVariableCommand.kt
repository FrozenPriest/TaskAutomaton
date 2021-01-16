package ru.frozenpriest.taskautomaton.program

class SetVariableCommand(val varName: String, val value: Any): Command {
    override fun perform(program: Program) {
        program.variables[varName] = value
    }
}