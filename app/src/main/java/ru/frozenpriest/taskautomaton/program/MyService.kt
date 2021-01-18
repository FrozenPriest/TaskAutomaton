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
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = 0
        val view = LayoutInflater.from(applicationContext).inflate(R.layout.view_holder_task, null)
        val textView: TextView = view.findViewById(R.id.textView2)
        textView.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        textView.setOnClickListener { windowManager.removeView(view) }
        windowManager.addView(view, params)
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