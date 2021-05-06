package ru.frozenpriest.taskautomaton.program.commands

abstract class Function(name: String, description: String, iconId: Int) :
    Command(name, description, iconId) {
    var functionResult: Boolean = false
        protected set
}