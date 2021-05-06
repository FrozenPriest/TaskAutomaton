package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.utils.HtmlViewBuilder

class ShowHtml(
    private val stringToShow: String,
    private val args: Array<String>,
    private val backgroundColor: Int = Color.WHITE,
    private val textColor: Int = Color.BLACK,
    private val gravity: Int = Gravity.CENTER,
    private val duration: Long = -1
) : Command(
    name = "Show pop-up",
    description = "",
    iconId = R.drawable.icon_sample
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