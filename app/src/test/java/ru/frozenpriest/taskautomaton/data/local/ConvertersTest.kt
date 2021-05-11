package ru.frozenpriest.taskautomaton.data.local

import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import junit.framework.TestCase
import org.junit.Assert
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

class ConvertersTest : TestCase() {

    fun testFromCommandList() {
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
        val expected =
            "[{\"type\":\"SetVar\",\"varName\":\"funkyVar1\",\"value\":true},{\"type\":\"SetVar\",\"varName\":\"funkyVar2\",\"value\":true},{\"type\":\"SetVar\",\"varName\":\"f3\",\"value\":0},{\"type\":\"SetVar\",\"varName\":\"f4\",\"value\":9},{\"type\":\"SetVar\",\"varName\":\"f10\",\"value\":10},{\"type\":\"VibrateWithPattern\",\"delays\":[200,100,200,100,400,200,500]},{\"type\":\"WhileCondition\",\"condition\":{\"type\":\"NotFunction\",\"function\":{\"type\":\"LowerVar\",\"var1\":\"f10\",\"var2\":\"f3\"}}},{\"type\":\"UseTts\",\"stringToShow\":\"Test is %s\",\"args\":[\"f3\"],\"language\":\"en\"},{\"type\":\"IncVar\",\"varName\":\"f3\"},{\"type\":\"EndWhile\"},{\"type\":\"IfCondition\",\"condition\":{\"type\":\"CheckVar\",\"varName\":\"funkyVar1\"}},{\"type\":\"IfCondition\",\"condition\":{\"type\":\"CheckVar\",\"varName\":\"funkyVar2\"}},{\"type\":\"ShowToast\",\"stringToShow\":\"Test test test\",\"args\":[],\"duration\":1},{\"type\":\"EndIf\"},{\"type\":\"ElseCondition\"},{\"type\":\"ShowToast\",\"stringToShow\":\"NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\",\"args\":[],\"duration\":1},{\"type\":\"EndElse\"},{\"type\":\"EndIf\"},{\"type\":\"IfCondition\",\"condition\":{\"type\":\"CheckVar\",\"varName\":\"funkyVar1\"}},{\"type\":\"ShowHtml\",\"stringToShow\":\"<html>\\n<body>\\n<h1 style=\\\"font-size:300%%;\\\">This is a heading</h1>\\n<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\\n</body>\\n</html>\\n\",\"args\":[\"funkyVar1\",\"funkyVar4\"],\"backgroundColor\":-16777216,\"textColor\":-1,\"gravity\":8388627,\"duration\":15000},{\"type\":\"EndIf\"},{\"type\":\"ElseCondition\"},{\"type\":\"ShowToast\",\"stringToShow\":\"New text %s, to go %s\",\"args\":[\"funkyVar1\",\"funkyVar2\"],\"duration\":1},{\"type\":\"EndElse\"}]"
        assertEquals(expected, Converters.fromCommandList(list))
    }

    fun testToCommandList() {
        val expected = listOf(
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
        val jsonString = Converters.fromCommandList(expected)
        val result = Converters.toCommandList(jsonString)
        val resultJson = Converters.fromCommandList(result)
        Assert.assertEquals(jsonString, resultJson)
    }

    fun testFromTrigger() {
        val trigger = LocationTrigger(
            1.0, 2.0, 3.0, LocationState.Undefined, LocationTriggerType.Enter, "test1", true
        )
        val expected =
            "{\"type\":\"LocationTrigger\",\"latitude\":1.0,\"longitude\":2.0,\"radius\":3.0,\"currentState\":\"Undefined\",\"type\":\"Enter\",\"programName\":\"test1\",\"enabled\":true}"
        val json = Converters.fromTrigger(trigger)
        assertEquals(expected, json)
    }

    fun testToTriggerLocation() {
        val json =
            "{\"type\":\"LocationTrigger\",\"latitude\":1.0,\"longitude\":2.0,\"radius\":3.0,\"currentState\":\"Undefined\",\"type\":\"Enter\",\"programName\":\"test1\",\"enabled\":true}"
        val expected = LocationTrigger(
            1.0, 2.0, 3.0, LocationState.Undefined, LocationTriggerType.Enter, "test1", true
        )
        val result = Converters.toTrigger(json) as LocationTrigger

        assertEquals(expected.type, result.type)
        assertEquals(expected.latitude, result.latitude)
        assertEquals(expected.longitude, result.longitude)
        assertEquals(expected.enabled, result.enabled)
        assertEquals(expected.programName, result.programName)
        assertEquals(expected.radius, result.radius)
    }

    fun testToTriggerTime() {
        val json =
            "{\"type\":\"TimeTrigger\",\"hour\":14,\"minute\":22,\"activeDays\":[\"MONDAY\",\"FRIDAY\",\"SUNDAY\"],\"programName\":\"another program\",\"enabled\":false}"
        val expected = TimeTrigger(
            14,
            22,
            listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY),
            "another program",
            false
        )
        val result = Converters.toTrigger(json) as TimeTrigger

        assertEquals(expected.hour, result.hour)
        assertEquals(expected.minute, result.minute)
        assertEquals(expected.activeDays, result.activeDays)
        assertEquals(expected.enabled, result.enabled)
        assertEquals(expected.programName, result.programName)
    }
}