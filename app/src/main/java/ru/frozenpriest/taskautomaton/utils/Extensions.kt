package ru.frozenpriest.taskautomaton.utils

import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity
import ru.frozenpriest.taskautomaton.program.Program

fun Program.toEntity(): ProgramEntity {
    return ProgramEntity(
        id = this.id,
        name = this.name,
        commands = this.commands
    )
}

fun ProgramEntity.toProgram(): Program {
    return Program(
        id = this.id,
        name = this.name,
        commands = this.commands
    )//may be add setLevels
}
