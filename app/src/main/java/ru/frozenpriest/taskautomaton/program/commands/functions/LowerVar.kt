package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("LowerVar")
class LowerVar(
    @JsonProperty("var1") val var1: String,
    @JsonProperty("var2") val var2: String
) :
    Function("Check lower", "$var1 < $var2", R.drawable.icon_sample, CommandBuilder.CommandClass.LowerVar) {


    override fun perform(program: Program, context: Context) {
        functionResult = try {
            val var1Value = program.variables[var1] as String
            val var2Value = program.variables[var2] as String
            var1Value.toDouble() < var2Value.toDouble()
        } catch (e: Exception) {
            false
        }
    }
}