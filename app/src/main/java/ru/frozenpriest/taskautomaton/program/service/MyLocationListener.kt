package ru.frozenpriest.taskautomaton.program.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger.Type.*

class MyLocationListener(
    private val triggerActivationListener: TriggerActivationListener,
    private val repository: RoomRepository,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope
) :
    LocationListener {
    var triggers: List<TriggerEntity> = listOf()


    override fun onLocationChanged(location: Location) {
        Log.e("LocationListener", triggers.size.toString())
        triggers.onEach { entry ->
            val locationTrigger = entry.trigger as LocationTrigger
            val dest = Location(LocationManager.GPS_PROVIDER).apply {
                latitude = locationTrigger.latitude
                longitude = locationTrigger.longitude
            }
            val distance = location.distanceTo(dest)
            val prevState = entry.trigger.currentState
            if (locationTrigger.radius > distance) entry.trigger.currentState = LocationState.Inside
            else entry.trigger.currentState = LocationState.Outside
            Log.e("LocationListener", "Entry ${entry.name} is ${entry.trigger.currentState}, distance: $distance")
            val triggerToStart = when (locationTrigger.type) {
                Enter -> (prevState != LocationState.Inside) && (entry.trigger.currentState == LocationState.Inside)
                Exit -> (prevState != LocationState.Outside) && (entry.trigger.currentState == LocationState.Outside)
                EnterOrExit -> entry.trigger.currentState != prevState
            }
            if (prevState != entry.trigger.currentState) lifecycleCoroutineScope.launch {
                repository.updateTrigger(entry)
            }

            Log.e("LocationListener", "Trigger? $triggerToStart")
            if (triggerToStart) {
                entry.connectedProgramId?.let {
                    triggerActivationListener.onTriggerLaunch(
                        it
                    )
                }
            }
        }

    }
}

enum class LocationState {
    @JsonProperty("Inside")
    Inside,

    @JsonProperty("Outside")
    Outside,

    @JsonProperty("Undefined")
    Undefined
}