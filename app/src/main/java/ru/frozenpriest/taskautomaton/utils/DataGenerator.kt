package ru.frozenpriest.taskautomaton.utils

import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.functions.CheckVar
import ru.frozenpriest.taskautomaton.program.commands.functions.LowerVar
import ru.frozenpriest.taskautomaton.program.commands.functions.NotFunction
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.IncVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar
import ru.frozenpriest.taskautomaton.program.service.LocationState
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.LocationTriggerType
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import java.time.DayOfWeek
import java.util.*
import kotlin.random.Random

object DataGenerator {
    fun getPrograms(): List<Program> {
        val result = mutableListOf<Program>()

        for (i in 1..10) {
            val list = listOf(
                SetVar("funkyVar1", true),
                SetVar("funkyVar2", true),
                SetVar("f3", 0),
                SetVar("f4", 9),
                SetVar("f10", 10),
                VibrateWithPattern(listOf(200, 100, 200, 100, 400, 200, 500)),
                WhileCondition(NotFunction(LowerVar("f10", "f3"))),
                UseTts("Test is %s", listOf("f3"), Locale.ENGLISH),
                IncVar("f3"),
                EndWhile(),

                IfCondition(CheckVar("funkyVar1")),
                IfCondition(CheckVar("funkyVar2")),
                ShowToast(
                    "Test test test",
                    listOf(),
                    Toast.LENGTH_LONG
                ),
                EndIf(),
                ElseCondition(),
                ShowToast(
                    "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                    listOf(),
                    Toast.LENGTH_LONG
                ),
                EndElse(),
                EndIf(),
                IfCondition(CheckVar("funkyVar1")),
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
                ),
                EndIf(),
                ElseCondition(),
                ShowToast(
                    "New text %s, to go %s",
                    listOf("funkyVar1", "funkyVar2"),
                    Toast.LENGTH_LONG
                ),
                EndElse()
            )
            result.add(Program(name = "Program $i", commands = list))
        }
        return result
    }

    fun getTriggers(): List<TriggerEntity> {
        val result = mutableListOf<TriggerEntity>()
        val random = Random.Default
        for (i in 1..10) {
            result.add(
                TriggerEntity(
                    connectedProgramId = 1,
                    name = "testName $i",
                    trigger = if (random.nextBoolean())
                        LocationTrigger(
                            random.nextDouble(),
                            random.nextDouble(),
                            random.nextDouble(100.0),
                            LocationState.Undefined,
                            LocationTriggerType.values()[random.nextInt(3)]
                        )
                    else
                        TimeTrigger(
                            random.nextInt(24),
                            random.nextInt(60),
                            listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)
                        ),
                    enabled = random.nextBoolean()
                )
            )
        }
        return result
    }
}