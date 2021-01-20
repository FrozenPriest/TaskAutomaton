package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.widget.Toast
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

/**
 * Show toast with variables
 * Example "New int is %s", var1
 * var1 is variable from program
 */
class ShowToast(val stringToShow: String, val args: Array<String>, val duration: Int): Command() {
    override fun perform(program: Program, context: Context) {
        val vars = args.map{ program.variables[it] }.toTypedArray()
        val fixedString = String.format(stringToShow, *vars)
        Toast.makeText(context, fixedString, duration).show()
    }
}