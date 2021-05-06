package ru.frozenpriest.taskautomaton.program.triggers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import java.time.DayOfWeek

@JsonTypeName("TimeTrigger")
class TimeTrigger(
    @JsonProperty("hour")
    var hour: Int,
    @JsonProperty("minute")
    var minute: Int,
    @JsonProperty("activeDays")
    var activeDays: List<DayOfWeek>,
    @JsonProperty("programName")
    programName: String,
    @JsonProperty("enabled")
    enabled: Boolean
) : Trigger(programName, enabled) {

}