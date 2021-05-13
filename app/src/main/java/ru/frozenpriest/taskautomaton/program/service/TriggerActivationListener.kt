package ru.frozenpriest.taskautomaton.program.service

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar

class TriggerActivationListener(val context: Context) {

    fun onTriggerLaunch(programId: Long) {
        /*todo
            find program with name
            load it with params
            launch it
         */
        if(programId == 1L) {
            val list = listOf(
                SetVar("funkyVar1", true),
                SetVar("funkyVar2", true),
                SetVar("f3", 0),
                SetVar("f4", 9),
                SetVar("f10", 10),
                VibrateWithPattern(listOf(200, 100, 200, 100, 400, 200, 500)),
                ShowHtml(
                    "<html>\n" +
                            "<body>\n" +
                            "<h1 style=\"font-size:300%%;\">This is a heading</h1>\n" +
                            "<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\n" +
                            "</body>\n" +
                            "</html>\n",
                    duration = 15000,
                    args = listOf("funkyVar1", "funkyVar4"),
                    textColor = Color.WHITE,
                    backgroundColor = Color.BLACK,
                    gravity = Gravity.START or Gravity.CENTER_VERTICAL
                )
            )
            //todo load params
            Program(1, "test", list).executeCommands(context)
        }
    }
}