package ru.frozenpriest.taskautomaton.program

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.NotificationCompat
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.utils.HtmlViewBuilder


class MyService : Service() {
    private lateinit var windowManager: WindowManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        showHtml("<html>\n" +
                "<body>\n" +
                "<h1 style=\"font-size:300%;\">This is a heading</h1>\n" +
                "<p>Do not forget to buy <mark>milk</mark> today.</p>\n" +
                "</body>\n" +
                "</html>\n")
    }

    fun showHtml(html: String) {
        HtmlViewBuilder(applicationContext, windowManager)
            .setBackgroundColor("#123343")
            .setTextColor("#665500")
            .setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
            .setHtml(html)
            .setExpireTime(10000)
            .build()



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
        val NOTIFICATION_CHANNEL_ID = "service_channel"
        val NOTIFICATION_CHANNEL_NAME = "Service channel"
        val NOTIFICATION_ID = 13
    }

}