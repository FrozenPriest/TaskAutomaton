package ru.frozenpriest.taskautomaton.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.MyService


class MainActivity : AppCompatActivity() {
    companion object {
        const val OVERLAY_PERMISSION_REQ_CODE = 1

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissionOverlay()


        val intent = Intent(this, MyService::class.java)
        intent.putExtra("ProgramId", "id") //todo when have database
        stopService(intent) //todo for debug purposes, remove later
        startService(intent)


    }


    fun checkPermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW")
            requestPermissions(
                arrayOf(Settings.ACTION_MANAGE_OVERLAY_PERMISSION),
                OVERLAY_PERMISSION_REQ_CODE
            )
        }
    }
}