package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.os.VibrationEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.App
import ru.frozenpriest.taskautomaton.program.Command
import ru.frozenpriest.taskautomaton.program.Program

class VibrateWithPattern(private val delays: Array<Long>) : Command() {
    override val commandName: String
        get() = "Vibrate"
    override val commandDescription: String
        get() = ""
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer
    override fun perform(program: Program, context: Context) {
        val effect = VibrationEffect.createWaveform(delays.toLongArray(), -1)
        App.vibrator.vibrate(effect)
    }
}