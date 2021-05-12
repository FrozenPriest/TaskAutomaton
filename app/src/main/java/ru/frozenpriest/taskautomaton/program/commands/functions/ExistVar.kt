package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

/**
 * varResult = isExist(varName)
 */
@JsonTypeName("ExistVar")
class ExistVar(
    @JsonProperty("varName")
    val varName: String
) :
    Function("Is exist", varName, R.drawable.icon_sample, CommandBuilder.CommandClass.ExistVar) {

    override fun perform(program: Program, context: Context) {
        functionResult = program.variables.containsKey(varName)
    }
}