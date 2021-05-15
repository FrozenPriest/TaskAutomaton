package ru.frozenpriest.taskautomaton.program.triggers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.program.service.LocationState
import java.time.DayOfWeek

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = LocationTrigger::class, name = "LocationTrigger"),
    JsonSubTypes.Type(value = TimeTrigger::class, name = "TimeTrigger"),
)
sealed class Trigger

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
    var type: Type,
) : Trigger() {

    enum class Type {
        @JsonProperty("Enter")
        Enter,

        @JsonProperty("Exit")
        Exit,

        @JsonProperty("EnterOrExit")
        EnterOrExit
    }
}

@JsonTypeName("TimeTrigger")
class TimeTrigger(
    @JsonProperty("hour")
    var hour: Int,
    @JsonProperty("minute")
    var minute: Int,
    @JsonProperty("activeDays")
    var activeDays: MutableList<DayOfWeek>,
) : Trigger()