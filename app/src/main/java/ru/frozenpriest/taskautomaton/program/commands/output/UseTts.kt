package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program
import java.util.*

class UseTts(val stringToShow: String, val args: Array<String>, val language: Locale) : Command() {
    override val commandName: String
        get() = "Speak text"
    override val commandDescription: String
        get() = "$stringToShow, lang = ${language.language}"
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val stringFormatted = String.format(stringToShow, *vars)
        App.mTts.language = language
        App.mTts.speak(stringFormatted, TextToSpeech.QUEUE_ADD, null, "")

    }


}