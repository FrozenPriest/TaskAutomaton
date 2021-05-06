package ru.frozenpriest.taskautomaton.program.commands.variables

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

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
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {

        val a = program.variables[varName1]
        val b = program.variables[varName2]
        if (a is Number && b is Number) {
            if (a is Double || b is Double || a is Float || b is Float) {
                program.variables[varRes] = (a.toDouble() / b.toDouble())
            } else {
                program.variables[varRes] = (a.toLong() / b.toLong())
            }
        }

    }
}