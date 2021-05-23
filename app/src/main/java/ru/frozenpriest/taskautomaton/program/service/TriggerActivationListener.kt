package ru.frozenpriest.taskautomaton.program.service

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.local.RoomRepository

class TriggerActivationListener(
    val context: Context,
    val repository: RoomRepository,
    val lifecycleCoroutineScope: LifecycleCoroutineScope
) {
    fun onTriggerLaunch(programId: Long) = lifecycleCoroutineScope.launch{
        if(programId == -1L) return@launch
        repository.getProgram(programId).executeCommands(context)

    }
}