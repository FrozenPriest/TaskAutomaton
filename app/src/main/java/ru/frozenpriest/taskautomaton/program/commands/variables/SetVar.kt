package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("SetVar")
class SetVar(
    @JsonProperty("varName")
    val varName: String,
    @JsonProperty("value")
    val value: Any) : Command(
    name = "Set variable",
    description = "$varName = $value",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables
) {

    override fun perform(program: Program, context: Context) {
        if (value is Function) {
            value.perform(program, context)
            program.variables[varName] = value.functionResult

        } else {
            program.variables[varName] = value
        }
    }
}