package ru.frozenpriest.taskautomaton.program.service.listeners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.service.TriggerActivationListener
import ru.frozenpriest.taskautomaton.program.triggers.SimpleEventTrigger


class SimpleEventListener(
    val context: Context,
    private val triggerActivationListener: TriggerActivationListener,
) : BroadcastReceiver() {
    var triggers: List<TriggerEntity> = listOf()


    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { action ->
            val trigger = triggers.find {
                (it.trigger as SimpleEventTrigger).eventAction == SimpleEventTrigger.Event.getEnum(action)
            }
            trigger?.let {
                Log.e(
                    "SimpleEventListener",
                    "Received event trigger ${trigger.name}, action: ${(it.trigger as SimpleEventTrigger).eventAction}"
                )
                trigger.connectedProgramId?.let { programId ->
                    triggerActivationListener.onTriggerLaunch(
                        programId
                    )
                }
                Intent.ACTION_AIRPLANE_MODE_CHANGED
            }

        }
    }

}