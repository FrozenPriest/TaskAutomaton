package ru.frozenpriest.taskautomaton.program.commands.system

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

/**
 * Save time
 */
@JsonTypeName("TimeToVar")
class TimeToVar(
    @JsonProperty("varRes")
    val varRes: String,
) : Command(
    name = "Save time to variable",
    description = "$varRes = Time in millis",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables,
    commandClass = CommandBuilder.CommandClass.TimeToVar
) {

    override fun perform(program: Program, context: Context) {
        program.variables[varRes] = Instant.now().toEpochMilli()

    }
}


/**
 * Save time from var to another var as text
 */
@JsonTypeName("TimeToVarText")
class TimeToVarText(
    @JsonProperty("varRes")
    val varRes: String,
    @JsonProperty("varTime")
    val varTime: String,
) : Command(
    name = "Set Clock Time to variable",
    description = "$varRes = Time($varTime)",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables,
    commandClass = CommandBuilder.CommandClass.TimeToVarText
) {

    override fun perform(program: Program, context: Context) {
        val time = program.variables[varTime] as Long
        program.variables[varRes] = LocalTime.from(
            Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
        ).toString()

    }
}

/**
 * Save time from var to another var as text
 */
@JsonTypeName("DateToVarText")
class DateToVarText(
    @JsonProperty("varRes")
    val varRes: String,
    @JsonProperty("varTime")
    val varTime: String,
) : Command(
    name = "Set date to variable",
    description = "$varRes = Date($varTime)",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables,
    commandClass = CommandBuilder.CommandClass.DateToVarText
) {

    override fun perform(program: Program, context: Context) {
        val time = program.variables[varTime] as Long
        program.variables[varRes] = LocalDate.from(
            Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
        ).toString()

    }
}