package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.MyService
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.utils.HtmlViewBuilder

class ShowHtml(
    private val stringToShow: String,
    private val args: Array<String>,
    private val backgroundColor: Int = Color.WHITE,
    private val textColor: Int = Color.BLACK,
    private val gravity: Int = Gravity.CENTER,
    private val duration: Long = -1
) : Command() {
    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val fixedHTML = String.format(stringToShow, *vars)
        //todo show html pop-up
        val builder = HtmlViewBuilder(context)
            .setBackgroundColor(backgroundColor)
            .setTextColor(textColor)
            .setGravity(gravity)
            .setHtml(fixedHTML)
            .setExpireTime(duration)
        MyService.windowManager.addView(builder.build(), builder.params)
    }
}