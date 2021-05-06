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
import java.util.*

class ConvertersTest : TestCase() {

    fun testFromCommandList() {
        val list = listOf(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", true),
            SetVar("f3", 0),
            SetVar("f4", 9),
            SetVar("f10", 10),
            VibrateWithPattern(arrayOf(200, 100, 200, 100, 400, 200, 500)),
            WhileCondition(NotFunction(LowerVar("f10", "f3"))),
            UseTts("Test is %s", arrayOf("f3"), Locale.ENGLISH),
            IncVar("f3"),
            EndWhile(),

            IfCondition(CheckVar("funkyVar1")),
            IfCondition(CheckVar("funkyVar2")),
            ShowToast(
                "Test test test",
                arrayOf(),
                Toast.LENGTH_LONG
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                arrayOf(),
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
                args = arrayOf("funkyVar1", "funkyVar4"),
                textColor = Color.WHITE,
                backgroundColor = Color.BLACK,
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "New text %s, to go %s",
                arrayOf("funkyVar1", "funkyVar2"),
                Toast.LENGTH_LONG
            ),
            EndElse()
        )
        val expected = "[ {\r\n" +
                "  \"type\" : \"SetVar\",\r\n" +
                "  \"varName\" : \"funkyVar1\",\r\n" +
                "  \"value\" : true\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"SetVar\",\r\n" +
                "  \"varName\" : \"funkyVar2\",\r\n" +
                "  \"value\" : true\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"SetVar\",\r\n" +
                "  \"varName\" : \"f3\",\r\n" +
                "  \"value\" : 0\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"SetVar\",\r\n" +
                "  \"varName\" : \"f4\",\r\n" +
                "  \"value\" : 9\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"SetVar\",\r\n" +
                "  \"varName\" : \"f10\",\r\n" +
                "  \"value\" : 10\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"VibrateWithPattern\",\r\n" +
                "  \"delays\" : [ 200, 100, 200, 100, 400, 200, 500 ]\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"WhileCondition\",\r\n" +
                "  \"condition\" : {\r\n" +
                "    \"type\" : \"NotFunction\",\r\n" +
                "    \"function\" : {\r\n" +
                "      \"type\" : \"LowerVar\",\r\n" +
                "      \"var1\" : \"f10\",\r\n" +
                "      \"var2\" : \"f3\"\r\n" +
                "    }\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"UseTts\",\r\n" +
                "  \"stringToShow\" : \"Test is %s\",\r\n" +
                "  \"args\" : [ \"f3\" ],\r\n" +
                "  \"language\" : \"en\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"IncVar\",\r\n" +
                "  \"varName\" : \"f3\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndWhile\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"IfCondition\",\r\n" +
                "  \"condition\" : {\r\n" +
                "    \"type\" : \"CheckVar\",\r\n" +
                "    \"varName\" : \"funkyVar1\"\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"IfCondition\",\r\n" +
                "  \"condition\" : {\r\n" +
                "    \"type\" : \"CheckVar\",\r\n" +
                "    \"varName\" : \"funkyVar2\"\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ShowToast\",\r\n" +
                "  \"stringToShow\" : \"Test test test\",\r\n" +
                "  \"args\" : [ ],\r\n" +
                "  \"duration\" : 1\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndIf\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ElseCondition\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ShowToast\",\r\n" +
                "  \"stringToShow\" : \"NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\",\r\n" +
                "  \"args\" : [ ],\r\n" +
                "  \"duration\" : 1\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndElse\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndIf\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"IfCondition\",\r\n" +
                "  \"condition\" : {\r\n" +
                "    \"type\" : \"CheckVar\",\r\n" +
                "    \"varName\" : \"funkyVar1\"\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ShowHtml\",\r\n" +
                "  \"stringToShow\" : \"<html>\\n<body>\\n<h1 style=\\\"font-size:300%%;\\\">This is a heading</h1>\\n<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\\n</body>\\n</html>\\n\",\r\n" +
                "  \"args\" : [ \"funkyVar1\", \"funkyVar4\" ],\r\n" +
                "  \"backgroundColor\" : -16777216,\r\n" +
                "  \"textColor\" : -1,\r\n" +
                "  \"gravity\" : 8388627,\r\n" +
                "  \"duration\" : 15000\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndIf\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ElseCondition\"\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"ShowToast\",\r\n" +
                "  \"stringToShow\" : \"New text %s, to go %s\",\r\n" +
                "  \"args\" : [ \"funkyVar1\", \"funkyVar2\" ],\r\n" +
                "  \"duration\" : 1\r\n" +
                "}, {\r\n" +
                "  \"type\" : \"EndElse\"\r\n" +
                "} ]"
        assertEquals(expected, Converters.fromCommandList(list))
    }

    fun testToCommandList() {
        val expected = listOf(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", true),
            SetVar("f3", 0),
            SetVar("f4", 9),
            SetVar("f10", 10),
            VibrateWithPattern(arrayOf(200, 100, 200, 100, 400, 200, 500)),
            WhileCondition(NotFunction(LowerVar("f10", "f3"))),
            UseTts("Test is %s", arrayOf("f3"), Locale.ENGLISH),
            IncVar("f3"),
            EndWhile(),

            IfCondition(CheckVar("funkyVar1")),
            IfCondition(CheckVar("funkyVar2")),
            ShowToast(
                "Test test test",
                arrayOf(),
                Toast.LENGTH_LONG
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                arrayOf(),
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
                args = arrayOf("funkyVar1", "funkyVar4"),
                textColor = Color.WHITE,
                backgroundColor = Color.BLACK,
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "New text %s, to go %s",
                arrayOf("funkyVar1", "funkyVar2"),
                Toast.LENGTH_LONG
            ),
            EndElse()
        )
        val jsonString = Converters.fromCommandList(expected)
        val result = Converters.toCommandList(jsonString)
        val resultJson = Converters.fromCommandList(result)
        Assert.assertEquals(jsonString, resultJson)
    }
}