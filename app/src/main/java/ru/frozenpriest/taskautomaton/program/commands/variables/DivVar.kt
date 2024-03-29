package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType

/**
 * Put varName1 / varName2 into varRes
 */
@JsonTypeName("DivVar")
class DivVar(
    @JsonProperty("varRes")
    val varRes: String,
    @JsonProperty("varName1")
    val varName1: String,
    @JsonProperty("varName2")
    val varName2: String
) : Command(
    name = "Divide two variables",
    description = "$varRes = $varName1 / $varName2",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Variables,
    commandClass = CommandBuilder.CommandClass.DivVar
) {

    override fun perform(program: Program, context: Context) {

        var a = program.variables[varName1]
        var b = program.variables[varName2]
        if(a is String) a = a.toDouble()
        if(b is String) b = b.toDouble()

        if (a is Number && b is Number) {
            if (a is Double || b is Double || a is Float || b is Float) {
                program.variables[varRes] = (a.toDouble() / b.toDouble()).toString()
            } else {
                program.variables[varRes] = (a.toLong() / b.toLong()).toString()
            }
        }

    }
}