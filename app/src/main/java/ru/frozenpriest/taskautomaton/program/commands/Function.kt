package ru.frozenpriest.taskautomaton.program.commands

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder

abstract class Function(name: String, description: String, iconId: Int, commandClass: CommandBuilder.CommandClass) :
    Command(name, description, iconId, CommandType.Logic, commandClass) {
    @JsonIgnore
    var functionResult: Boolean = false
        protected set
}