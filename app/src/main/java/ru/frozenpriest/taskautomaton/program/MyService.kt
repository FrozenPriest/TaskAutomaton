package ru.frozenpriest.taskautomaton.program

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.commands.functions.CheckVar
import ru.frozenpriest.taskautomaton.program.commands.functions.LowerVar
import ru.frozenpriest.taskautomaton.program.commands.functions.NotFunction
import ru.frozenpriest.taskautomaton.program.commands.logic.*
import ru.frozenpriest.taskautomaton.program.commands.output.ShowHtml
import ru.frozenpriest.taskautomaton.program.commands.output.ShowToast
import ru.frozenpriest.taskautomaton.program.commands.output.UseTts
import ru.frozenpriest.taskautomaton.program.commands.output.VibrateWithPattern
import ru.frozenpriest.taskautomaton.program.commands.variables.IncVar
import ru.frozenpriest.taskautomaton.program.commands.variables.SetVar
import java.util.*

class MyService : Service(), TextToSpeech.OnInitListener {

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
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // creating TTS
        mTts = TextToSpeech(this, this)
        //creating vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


    }

    private fun executeDebug() {
        val list = listOf(
            SetVar("funkyVar1", true),
            SetVar("funkyVar2", true),
            SetVar("f3", 0),
            SetVar("f4", 9),
            SetVar("f10", 10),
            VibrateWithPattern(arrayOf(200, 100, 200, 100, 400, 200, 500)),
            WhileCondition(NotFunction(LowerVar("f10", "f3"))),
                UseTts("Test is %s", arrayOf("f3"), Locale.ENGLISH),
                IncVar("f3"),
            EndWhile(),

            IfCondition(CheckVar("funkyVar1")),
                IfCondition(CheckVar("funkyVar2")),
                    ShowToast(
                        "Test test test",
                        arrayOf(),
                        Toast.LENGTH_LONG
                    ),
                EndIf(),
                ElseCondition(),
                    ShowToast(
                        "NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                        arrayOf(),
                        Toast.LENGTH_LONG
                    ),
                EndElse(),
            EndIf(),
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


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = mTts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                mTts.stop()
                executeDebug()
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "service_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Service channel"
        const val NOTIFICATION_ID = 13
        lateinit var windowManager: WindowManager
        lateinit var mTts: TextToSpeech
        lateinit var vibrator: Vibrator

    }
}
