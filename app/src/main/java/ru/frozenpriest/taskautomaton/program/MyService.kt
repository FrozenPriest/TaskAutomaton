package ru.frozenpriest.taskautomaton.program

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.gui.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.gui.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.logic.ElseCondition
import ru.frozenpriest.taskautomaton.program.commands.logic.EndElse
import ru.frozenpriest.taskautomaton.program.commands.logic.EndIf
import ru.frozenpriest.taskautomaton.program.commands.logic.IfCondition
import ru.frozenpriest.taskautomaton.program.commands.variables.AddVar
import ru.frozenpriest.taskautomaton.program.commands.variables.CheckVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar

class MyService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val list = listOf(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", 5),
            SetVar("funkyVar3", 3.0),
            SetVar("funkyVar4", 2),
            SetVar("funkyVar5", -6),
            AddVar("funkyVar888", "funkyVar2", "funkyVar3"),
            AddVar("funkyVar999", "funkyVar2", "funkyVar5"),

            IfCondition(CheckVar("funkyVar1")),
            ShowHtml(
                "<html>\n" +
                        "<body>\n" +
                        "<h1 style=\"font-size:300%%;\">This is a heading</h1>\n" +
                        "<p>Do not (%s, %s) forget to buy <mark>milk</mark> today.</p>\n" +
                        "</body>\n" +
                        "</html>\n",
                duration = 15000,
                args = arrayOf("funkyVar1", "funkyVar4"),
                textColor = Color.WHITE,
                backgroundColor = Color.BLACK,
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            ),
            EndIf(),
            ElseCondition(),
            ShowToast(
                "New text %s, to go %s",
                arrayOf("funkyVar1", "funkyVar2"),
                Toast.LENGTH_LONG
            ),
            EndElse()
        )

        val program = Program(list)

        program.executeCommands(applicationContext)
    }
    
    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)

        val notification =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Useless notification")
                .setContentText("Needed for foreground")
                .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "service_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Service channel"
        const val NOTIFICATION_ID = 13
        lateinit var windowManager: WindowManager
    }
}
