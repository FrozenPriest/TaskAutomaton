package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.os.VibrationEffect
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command

class VibrateWithPattern(private val delays: Array<Long>) : Command(
    name = "Vibrate",
    description = "",
    iconId = R.drawable.icon_sample
) {

    override fun perform(program: Program, context: Context) {
        val effect = VibrationEffect.createWaveform(delays.toLongArray(), -1)
        App.vibrator.vibrate(effect)
    }
}