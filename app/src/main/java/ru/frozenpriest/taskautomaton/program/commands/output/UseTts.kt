package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.speech.tts.TextToSpeech
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import java.util.*

@JsonTypeName("UseTts")
class UseTts(
    @JsonProperty("stringToShow")
    val stringToShow: String,
    @JsonProperty("args")
    val args: List<String>,
    @JsonProperty("language")
    val language: Locale
) : Command(
    name = "Speak text",
    description = "$stringToShow, lang = ${language.language}",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Output
) {

    override fun perform(program: Program, context: Context) {
        val vars = args.map { program.variables[it] }.toTypedArray()
        val stringFormatted = String.format(stringToShow, *vars)
        App.mTts.language = language
        App.mTts.speak(stringFormatted, TextToSpeech.QUEUE_ADD, null, "")

    }


}