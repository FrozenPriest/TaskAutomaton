package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.os.VibrationEffect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

@JsonTypeName("VibrateWithPattern")
class VibrateWithPattern(
    @JsonProperty("delays")
    val delays: Array<Long>
) : Command(
    name = "Vibrate",
    description = "",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        val effect = VibrationEffect.createWaveform(delays.toLongArray(), -1)
        App.vibrator.vibrate(effect)
    }
}