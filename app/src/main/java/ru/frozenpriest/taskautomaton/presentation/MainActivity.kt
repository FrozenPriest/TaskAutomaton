package ru.frozenpriest.taskautomaton.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.program.service.MyService

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    companion object {
        const val OVERLAY_PERMISSION_REQ_CODE = 1

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.programFragment -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }



        checkPermissionOverlay()


        val intent = Intent(this, MyService::class.java)
        stopService(intent) //todo for debug purposes, remove later
        startService(intent)


    }


    private fun checkPermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW")
            requestPermissions(
                arrayOf(Settings.ACTION_MANAGE_OVERLAY_PERMISSION),
                OVERLAY_PERMISSION_REQ_CODE
            )
        }
    }
}