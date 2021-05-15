package ru.frozenpriest.taskautomaton.program.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters


class MyTimeListener(
    val context: Context,
    private val triggerActivationListener: TriggerActivationListener,
) : BroadcastReceiver() {
    var triggers: List<TriggerEntity> = listOf()


    override fun onReceive(context: Context, intent: Intent) {
        val triggerId = intent.getLongExtra("triggerId", -1)
        val triggerNullable = triggers.find { it.id == triggerId }
        triggerNullable?.let { trigger ->
            Log.e("TimeListener", "Got time trigger: ${trigger.name}")
            setAlarm(
                findDayOfWeek(trigger),
                (trigger.trigger as TimeTrigger).hour,
                trigger.trigger.minute,
                triggerId
            )

            trigger.connectedProgramId?.let { programId ->
                triggerActivationListener.onTriggerLaunch(
                    programId
                )
            }
        }


    }


    private fun setAlarm(
        dayOfWeek: DayOfWeek,
        hour: Int,
        minute: Int,
        triggerId: Long,
    ) {
        val alarmMgr: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmTime = LocalDate.now()
            .with(TemporalAdjusters.nextOrSame(dayOfWeek))
            .atTime(hour, minute)
            .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()


        val intent = Intent(MyService.INTENT_ACTION_ALARM).apply {
            putExtra("triggerId", triggerId)
        }
        val sender = PendingIntent.getBroadcast(
            context,
            triggerId.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, alarmTime, sender)
    }

    fun updateTriggers(value: List<TriggerEntity>) {
        val alarmMgr: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (trigger in triggers) {
            val alarmIntent: PendingIntent = Intent(MyService.INTENT_ACTION_ALARM).let { intent ->
                PendingIntent.getBroadcast(
                    context,
                    trigger.id.toInt(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            alarmMgr.cancel(alarmIntent)
        }
        triggers = value
        setAlarms()
    }

    private fun setAlarms() {
        for (trigger in triggers) {
            setAlarm(
                findDayOfWeek(trigger),
                (trigger.trigger as TimeTrigger).hour,
                trigger.trigger.minute,
                trigger.id
            )
        }
    }

    private fun findDayOfWeek(triggerEntity: TriggerEntity): DayOfWeek {
        (triggerEntity.trigger as TimeTrigger).activeDays.sort()
        val currentTime = Instant.now()
        val alarmTime = LocalDate.now()
            .atTime(triggerEntity.trigger.hour, triggerEntity.trigger.minute)
            .atZone(ZoneId.systemDefault())
            .toInstant()
        var dayOfWeek = DayOfWeek.from(LocalDate.now())

        if (currentTime > alarmTime) {
            dayOfWeek = dayOfWeek.plus(1)
        }
        return triggerEntity.trigger.activeDays.find { it.value >= dayOfWeek.value }
            ?: triggerEntity.trigger.activeDays.first()
    }

}