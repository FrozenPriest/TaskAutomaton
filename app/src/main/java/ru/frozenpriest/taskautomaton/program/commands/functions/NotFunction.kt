package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Function

@JsonTypeName("NotFunction")
class NotFunction(
    @JsonProperty("function")
    val function: Function
) :
    Function("Not", function.info.name, R.drawable.icon_sample, CommandBuilder.CommandClass.NotFunction) {

    override fun perform(program: Program, context: Context) {
        function.perform(program, context)
        functionResult = !function.functionResult
    }
}