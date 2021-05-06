package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("EqualVar")
class EqualVar(
    @JsonProperty("var1")
    val var1: String,
    @JsonProperty("var2")
    val var2: String
) :
    Function("Check equal", "$var1 == $var2", R.drawable.icon_sample) {


    override fun perform(program: Program, context: Context) {
        val var1Value = program.variables[var1].toString()
        val var2Value = program.variables[var2].toString()
        functionResult = var1Value == var2Value
    }
}