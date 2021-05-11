package ru.frozenpriest.taskautomaton.program.commands

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class Function(name: String, description: String, iconId: Int) :
    Command(name, description, iconId, CommandType.Logic) {
    @JsonIgnore
    var functionResult: Boolean = false
        protected set
}