package ru.frozenpriest.taskautomaton.presentation.ui.startup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import ru.frozenpriest.taskautomaton.program.service.MyService

class AutoClosingActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
        startService(intent)

        finish()
    }
}