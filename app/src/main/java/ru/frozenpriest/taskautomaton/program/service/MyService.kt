package ru.frozenpriest.taskautomaton.program.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.program.service.listeners.MyLocationListener
import ru.frozenpriest.taskautomaton.program.service.listeners.MyTimeListener
import ru.frozenpriest.taskautomaton.program.service.listeners.SimpleEventListener
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.SimpleEventTrigger
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import javax.inject.Inject

@AndroidEntryPoint
class MyService : LifecycleService() {

    @Inject
    lateinit var repository: RoomRepository

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
    private lateinit var timeListener: MyTimeListener
    private lateinit var simpleEventListener: SimpleEventListener


    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        triggerActivationListener =
            TriggerActivationListener(applicationContext, repository, lifecycleScope)
        locationListener = MyLocationListener(triggerActivationListener, repository, lifecycleScope)

        timeListener = MyTimeListener(applicationContext, triggerActivationListener)


        createLocationManager()
        registerReceiver(timeListener, IntentFilter(INTENT_ACTION_ALARM))
        simpleEventListener = SimpleEventListener(applicationContext, triggerActivationListener)
        registerReceiver(simpleEventListener, IntentFilter().apply {
            SimpleEventTrigger.Event.values()
                .filter { it != SimpleEventTrigger.Event.Unspecified }.map { it.intent }
                .forEach {
                    addAction(it)
                }
        }
        )

        repository.allTriggers.observe(this, { triggers ->
            triggers.groupBy { it.trigger::class }.forEach { entry ->
                when (entry.key) {
                    LocationTrigger::class -> {
                        locationListener.triggers = entry.value.filter { it.enabled }
                    }
                    TimeTrigger::class -> {
                        timeListener.updateTriggers(entry.value.filter { it.enabled && (it.trigger as TimeTrigger).activeDays.isNotEmpty() })
                    }
                    SimpleEventTrigger::class -> {
                        simpleEventListener.triggers = entry.value.filter { it.enabled }
                    }
                    else -> throw NotImplementedError("Trigger not handled")
                }
            }

        })
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
        unregisterReceiver(timeListener)
        unregisterReceiver(simpleEventListener)
        super.onDestroy()
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
        const val INTENT_ACTION_ALARM = "MY_TIME_INTENT_FILTER"

    }
}
