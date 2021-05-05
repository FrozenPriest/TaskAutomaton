package ru.frozenpriest.taskautomaton.program.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import ru.frozenpriest.taskautomaton.R


class MyService : Service() {

    /*
    todo
        Генерируемый звуковой сигнал
         Уведомление
         Запуск приложения

         Wifi on/off
         Bluetooth on/off
         DnD on/off
         NFC on/off
         Wake device
         
     */
    private lateinit var locationManager: LocationManager

    private lateinit var triggerActivationListener: TriggerActivationListener
    private lateinit var locationListener: MyLocationListener

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        triggerActivationListener = TriggerActivationListener(applicationContext)
        locationListener = MyLocationListener(triggerActivationListener)
        createLocationManager()
    }

    private fun createLocationManager() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 2000, 10f, locationListener
        )
    }

    override fun onDestroy() {
        locationManager.removeUpdates(locationListener)
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

    }
}