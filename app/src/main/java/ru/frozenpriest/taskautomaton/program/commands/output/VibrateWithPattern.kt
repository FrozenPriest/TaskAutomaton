package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.os.VibrationEffect
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.MyService
import ru.frozenpriest.taskautomaton.program.Program

class VibrateWithPattern(private val delays: Array<Long>) : Command() {
    override fun perform(program: Program, context: Context) {
        val effect = VibrationEffect.createWaveform(delays.toLongArray(), -1)
        MyService.vibrator.vibrate(effect)
    }
}