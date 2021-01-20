package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.speech.tts.TextToSpeech
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.MyService
import ru.frozenpriest.taskautomaton.program.Program
import java.util.*

class UseTts(val stringToShow: String, val args: Array<String>, val language: Locale) : Command() {

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val stringFormatted = String.format(stringToShow, *vars)
        MyService.mTts.language = language
        MyService.mTts.speak(stringFormatted, TextToSpeech.QUEUE_ADD, null, "")

    }


}