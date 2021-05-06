package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.widget.Toast
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

/**
 * Show toast with variables
 * Example "New int is %s", var1
 * var1 is variable from program
 */
@JsonTypeName("ShowToast")
class ShowToast(
    @JsonProperty("stringToShow")
    val stringToShow: String,
    @JsonProperty("args")
    val args: Array<String>,
    @JsonProperty("duration")
    val duration: Int
) : Command(
    name = "Show toast",
    description = "$stringToShow, duration = ${
        if (duration == Toast.LENGTH_LONG) "long" else "short"
    }",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val fixedString = String.format(stringToShow, *vars)
        Toast.makeText(context, fixedString, duration).show()
    }
}