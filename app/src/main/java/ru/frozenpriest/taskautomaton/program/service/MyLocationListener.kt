package ru.frozenpriest.taskautomaton.program.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.LocationTriggerType.*

class MyLocationListener(private val triggerActivationListener: TriggerActivationListener) : LocationListener {
    private val triggers: List<TriggerWithState> =
        listOf(
            TriggerWithState(
                LocationTrigger(59.991273981908556, 30.3189792548688, 50.0, EnterOrExit, "test", true),
                LocationState.Undefined
            )
        )

    override fun onLocationChanged(location: Location) {
        triggers.onEach { entry ->
            val dest = Location(LocationManager.GPS_PROVIDER).apply {
                latitude = entry.locationTrigger.latitude
                longitude = entry.locationTrigger.longitude
            }
            val distance = location.distanceTo(dest)
            val prevState = entry.state
            if (entry.locationTrigger.radius > distance) entry.state = LocationState.Inside
            else entry.state = LocationState.Outside
            Log.e("LocationListener", "Entry is ${entry.state}, distance: $distance")
            val triggerToStart = when (entry.locationTrigger.type) {
                Enter -> (prevState != LocationState.Inside) && (entry.state == LocationState.Inside)
                Exit -> (prevState != LocationState.Outside) && (entry.state == LocationState.Outside)
                EnterOrExit -> entry.state != prevState
            }

            Log.e("LocationListener", "Trigger? $triggerToStart")
            if (triggerToStart) {
                triggerActivationListener.onTriggerLaunch(entry.locationTrigger.programName)
            }
        }

    }


    private enum class LocationState {
        Inside, Outside, Undefined
    }

    private data class TriggerWithState(
        val locationTrigger: LocationTrigger,
        var state: LocationState
    )
}