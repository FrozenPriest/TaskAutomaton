package ru.frozenpriest.taskautomaton.program.commands.functions

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import ru.frozenpriest.taskautomaton.program.Function
import ru.frozenpriest.taskautomaton.program.Program

/**
 * varResult = isExist(varName)
 */
class ExistVar(val varName: String): Function() {
    override val commandName: String
        get() = "Is exist"
    override val commandDescription: String
        get() = varName
    override val iconVector: ImageVector
        get() = Icons.Default.QuestionAnswer


    override fun perform(program: Program, context: Context) {
        functionResult = program.variables.containsKey(varName)
    }
}