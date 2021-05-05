package ru.frozenpriest.taskautomaton.program.triggers

data class LocationTrigger(
    val latitude: Double,
    val longitude: Double,
    val radius: Double,
    val type: LocationTriggerType,
    val programName: String
)

enum class LocationTriggerType {
    Enter,
    Exit,
    EnterOrExit
}
