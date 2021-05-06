package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.speech.tts.TextToSpeech
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import java.util.*

class UseTts(val stringToShow: String, val args: Array<String>, val language: Locale) : Command(
    name = "Speak text",
    description = "$stringToShow, lang = ${language.language}",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val stringFormatted = String.format(stringToShow, *vars)
        App.mTts.language = language
        App.mTts.speak(stringFormatted, TextToSpeech.QUEUE_ADD, null, "")

    }


}