package ru.frozenpriest.taskautomaton.program.triggers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.program.service.LocationState

@JsonTypeName("LocationTrigger")
class LocationTrigger(
    @JsonProperty("latitude")
    var latitude: Double,
    @JsonProperty("longitude")
    var longitude: Double,
    @JsonProperty("radius")
    var radius: Double,
    @JsonProperty("currentState")
    var currentState: LocationState,
    @JsonProperty("type")
    var type: LocationTriggerType,
    @JsonProperty("programName")
    programName: String,
    @JsonProperty("enabled")
    enabled: Boolean
) : Trigger(programName, enabled)

enum class LocationTriggerType {
    @JsonProperty("Enter")
    Enter,

    @JsonProperty("Exit")
    Exit,

    @JsonProperty("EnterOrExit")
    EnterOrExit
}
