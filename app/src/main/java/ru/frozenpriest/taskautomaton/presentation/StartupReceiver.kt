package ru.frozenpriest.taskautomaton.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val i = Intent(context, AutoClosingActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}