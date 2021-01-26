package ru.frozenpriest.taskautomaton

import android.app.Application
import android.content.Context
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.WindowManager
import java.util.*


class App : Application(), TextToSpeech.OnInitListener {
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // creating TTS
        mTts = TextToSpeech(this, this)
        //creating vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = mTts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                mTts.stop()
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    companion object {
        lateinit var windowManager: WindowManager
        lateinit var mTts: TextToSpeech
        lateinit var vibrator: Vibrator
    }
}