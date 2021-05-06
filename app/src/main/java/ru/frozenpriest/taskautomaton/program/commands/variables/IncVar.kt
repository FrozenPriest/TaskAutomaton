package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

@JsonTypeName("IncVar")
class IncVar(
    @JsonProperty("varName")
    val varName: String
) : Command(
    name = "Increment",
    description = "$varName++",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        var variable = program.variables[varName] as Int
        variable++
        program.variables[varName] = variable

    }
}