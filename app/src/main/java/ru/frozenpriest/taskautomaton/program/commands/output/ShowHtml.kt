package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import ru.frozenpriest.taskautomaton.utils.HtmlViewBuilder

@JsonTypeName("ShowHtml")
class ShowHtml( //name, args, backColor, textColor, gravity, duration
    @JsonProperty("stringToShow")
    val stringToShow: String,
    @JsonProperty("args")
    val args: List<String>,
    @JsonProperty("backgroundColor")
    val backgroundColor: Int = Color.WHITE,
    @JsonProperty("textColor")
    val textColor: Int = Color.BLACK,
    @JsonProperty("gravity")
    val gravity: Int = Gravity.CENTER,
    @JsonProperty("duration")
    val duration: Long = -1
) : Command(
    name = "Show pop-up",
    description = "",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Output,
    commandClass = CommandBuilder.CommandClass.ShowHtml
) {

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val fixedHTML = String.format(stringToShow, *vars)

        val builder = HtmlViewBuilder(context)
            .setBackgroundColor(backgroundColor)
            .setTextColor(textColor)
            .setGravity(gravity)
            .setHtml(fixedHTML)
            .setExpireTime(duration)
        App.windowManager.addView(builder.build(), builder.params)
    }
}