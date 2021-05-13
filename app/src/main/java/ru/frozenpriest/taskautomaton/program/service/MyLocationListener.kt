package ru.frozenpriest.taskautomaton.program.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.LocationTriggerType.*

class MyLocationListener(private val triggerActivationListener: TriggerActivationListener) :
    LocationListener {
    private val triggers: List<TriggerWithState> =
        listOf(
            TriggerWithState(
                TriggerEntity(
                    name = "namw",
                    connectedProgramId = 1L,
                    enabled = true,
                    trigger = LocationTrigger(
                        59.991273981908556,
                        30.3189792548688,
                        50.0,
                        LocationState.Outside,
                        EnterOrExit
                    )
                ),
                LocationState.Undefined
            )
        )

    override fun onLocationChanged(location: Location) {
        triggers.onEach { entry ->
            val locationTrigger = entry.locationTrigger.trigger as LocationTrigger
            val dest = Location(LocationManager.GPS_PROVIDER).apply {
                latitude = locationTrigger.latitude
                longitude = locationTrigger.longitude
            }
            val distance = location.distanceTo(dest)
            val prevState = entry.state
            if (locationTrigger.radius > distance) entry.state = LocationState.Inside
            else entry.state = LocationState.Outside
            Log.e("LocationListener", "Entry is ${entry.state}, distance: $distance")
            val triggerToStart = when (locationTrigger.type) {
                Enter -> (prevState != LocationState.Inside) && (entry.state == LocationState.Inside)
                Exit -> (prevState != LocationState.Outside) && (entry.state == LocationState.Outside)
                EnterOrExit -> entry.state != prevState
            }

            Log.e("LocationListener", "Trigger? $triggerToStart")
            if (triggerToStart) {
                triggerActivationListener.onTriggerLaunch(entry.locationTrigger.connectedProgramId)
            }
        }

    }


    private data class TriggerWithState(
        val locationTrigger: TriggerEntity,
        var state: LocationState
    )
}

enum class LocationState {
    @JsonProperty("Inside")
    Inside,

    @JsonProperty("Outside")
    Outside,

    @JsonProperty("Undefined")
    Undefined
}