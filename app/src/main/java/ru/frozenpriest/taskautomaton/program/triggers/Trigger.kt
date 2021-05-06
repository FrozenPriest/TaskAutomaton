package ru.frozenpriest.taskautomaton.program.triggers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = LocationTrigger::class, name = "LocationTrigger"),
    JsonSubTypes.Type(value = TimeTrigger::class, name = "TimeTrigger"),
)
abstract class Trigger(
    @JsonProperty("programName")
    var programName: String,
    @JsonProperty("enabled")
    var enabled: Boolean
) {

}