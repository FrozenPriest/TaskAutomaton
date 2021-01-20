package ru.frozenpriest.taskautomaton

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.frozenpriest.taskautomaton.program.MyService


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var tv: TextView
        const val OVERLAY_PERMISSION_REQ_CODE = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)

//        val list = listOf<Command>(
//            SetVar("funkyVar1", true),
//            SetVar("funkyVar2", 5),
//            SetVar("funkyVar3", 3.0),
//            SetVar("funkyVar4", 2),
//            SetVar("funkyVar5", -6),
//            AddVar("funkyVar888", "funkyVar2", "funkyVar3"),
//            AddVar("funkyVar999", "funkyVar2", "funkyVar5"),
//            ShowToast("New text %s, to go %s", arrayOf("funkyVar1", "funkyVar2"), Toast.LENGTH_LONG)
//
//        )
//
//        val program = Program(list)
//
//        program.executeCommands(applicationContext)

        checkPermissionOverlay()

        val intent = Intent(this, MyService::class.java)
        stopService(intent) //todo for debug purposes, remove later
        startService(intent)
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val intentSettings = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intentSettings, OVERLAY_PERMISSION_REQ_CODE)
        }
    }
}