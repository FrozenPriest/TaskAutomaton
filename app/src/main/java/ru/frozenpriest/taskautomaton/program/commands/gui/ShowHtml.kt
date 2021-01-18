package ru.frozenpriest.taskautomaton.program.commands.gui

import android.content.Context
import android.text.Html
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class ShowHtml(val stringToShow: String, val args: Array<String>, val duration: Int) : Command {
    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val fixedHTML = String.format(stringToShow, *vars)
        //todo show html pop-up
       // context.startActivity()
    }
}