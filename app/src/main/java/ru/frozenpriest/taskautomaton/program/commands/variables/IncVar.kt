package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

@JsonTypeName("IncVar")
class IncVar(
    @JsonProperty("varName")
    val varName: String
) : Command(
    name = "Increment",
    description = "$varName++",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables,
    commandClass = CommandBuilder.CommandClass.IncVar
) {

    override fun perform(program: Program, context: Context) {
        var variable = (program.variables[varName] as String).toInt()
        variable++
        program.variables[varName] = variable.toString()

    }
}