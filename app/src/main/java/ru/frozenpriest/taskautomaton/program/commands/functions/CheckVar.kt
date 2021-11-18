package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("CheckVar")
class CheckVar(
    @JsonProperty("varName")
    val varName: String
) :
    Function("Check as boolean", varName, R.drawable.icon_sample, CommandBuilder.CommandClass.CheckVar) {


    override fun perform(program: Program, context: Context) {
        val ev = ExistVar(varName)
        ev.perform(program, context)
        if (ev.functionResult) {
            val variable = program.variables[varName]
                functionResult = (variable as String).toBoolean()
                return
        }
    }
}