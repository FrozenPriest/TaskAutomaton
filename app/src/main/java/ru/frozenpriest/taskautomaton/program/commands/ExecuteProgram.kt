package ru.frozenpriest.taskautomaton.program.commands

import android.content.Context
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program

@JsonTypeName("ExecuteProgram")
class ExecuteProgram(
    @JsonProperty("program")
    val program: String,
    @JsonProperty("args")
    val args: HashMap<String, Any>
) : Command(
    name = "Execute $program with args",
    description = "",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        //todo later
        //get program by name
        //put args in it
        //executeCommands
    }
}