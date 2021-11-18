package ru.frozenpriest.taskautomaton.program.triggers

import ru.frozenpriest.taskautomaton.program.Program

data class Task(
    var enabled: Boolean,
    var trigger: LocationTrigger,
    var program: Program?
)