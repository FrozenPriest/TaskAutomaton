package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("EqualVar")
class EqualVar(
    @JsonProperty("var1")
    val var1: String,
    @JsonProperty("var2")
    val var2: String
) :
    Function(
        "Check equal",
        "$var1 == $var2",
        R.drawable.icon_sample,
        CommandBuilder.CommandClass.EqualVar
    ) {


    override fun perform(program: Program, context: Context) {
        functionResult = try {
            val var1Value = program.variables[var1].toString()
            val var2Value = program.variables[var2].toString()
            var1Value == var2Value
        } catch (e: Exception) {
            false
        }
    }
}