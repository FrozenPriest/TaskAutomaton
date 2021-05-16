package ru.frozenpriest.taskautomaton.program.triggers

import android.content.Intent
import android.media.AudioManager
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.program.service.listeners.LocationState
import java.time.DayOfWeek

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = LocationTrigger::class, name = "LocationTrigger"),
    JsonSubTypes.Type(value = TimeTrigger::class, name = "TimeTrigger"),
    JsonSubTypes.Type(value = SimpleEventTrigger::class, name = "SimpleEventTrigger"),

    )
sealed class Trigger

@JsonTypeName("LocationTrigger")
class LocationTrigger(
    @JsonProperty("latitude")
    var latitude: Double,
    @JsonProperty("longitude")
    var longitude: Double,
    @JsonProperty("radius")
    var radius: Double,
    @JsonProperty("currentState")
    var currentState: LocationState,
    @JsonProperty("type")
    var type: Type,
) : Trigger() {

    enum class Type {
        @JsonProperty("Enter")
        Enter,

        @JsonProperty("Exit")
        Exit,

        @JsonProperty("EnterOrExit")
        EnterOrExit
    }
}

@JsonTypeName("TimeTrigger")
class TimeTrigger(
    @JsonProperty("hour")
    var hour: Int,
    @JsonProperty("minute")
    var minute: Int,
    @JsonProperty("activeDays")
    var activeDays: MutableList<DayOfWeek>,
) : Trigger()

@JsonTypeName("SimpleEventTrigger")
class SimpleEventTrigger(
    @JsonProperty("eventAction")
    var eventAction: Event
) : Trigger() {


    enum class Event(val prettyString: String, val intent: String) {
        @JsonProperty("ACTION_POWER_CONNECTED")
        PowerConnected("Power connected", Intent.ACTION_POWER_CONNECTED),

        @JsonProperty("ACTION_POWER_DISCONNECTED")
        PowerDisconnected("Power disconnected", Intent.ACTION_POWER_DISCONNECTED),

        @JsonProperty("ACTION_AIRPLANE_MODE_CHANGED")
        AirplaneSwitched("Airplane switched", Intent.ACTION_AIRPLANE_MODE_CHANGED),

        @JsonProperty("ACTION_BATTERY_LOW")
        BatteryLow("Battery is low", Intent.ACTION_BATTERY_LOW),

        @JsonProperty("ACTION_BATTERY_OKAY")
        BatteryOkay("Battery is okay", Intent.ACTION_BATTERY_OKAY),

        @JsonProperty("ACTION_CAMERA_BUTTON")
        CameraButtonPressed("Camera button pressed", Intent.ACTION_CAMERA_BUTTON),

        @JsonProperty("ACTION_CONFIGURATION_CHANGED")
        ConfigurationChanged("Configuration changed", Intent.ACTION_CONFIGURATION_CHANGED),

        @JsonProperty("ACTION_DOCK_EVENT")
        DockEvent("Dock state changed", Intent.ACTION_DOCK_EVENT),

        @JsonProperty("ACTION_HEADSET_PLUG")
        HeadsetPlugged("Headset (dis)connected", AudioManager.ACTION_HEADSET_PLUG),

        @JsonProperty("ACTION_PROVIDER_CHANGED")
        ProviderChanged("Provider changed", Intent.ACTION_PROVIDER_CHANGED),

        @JsonProperty("ACTION_SCREEN_ON")
        ScreenOn("Screen on", Intent.ACTION_SCREEN_ON),

        @JsonProperty("ACTION_SCREEN_OFF")
        ScreenOff("Screen off", Intent.ACTION_SCREEN_OFF),


        @JsonProperty
        Unspecified("Unspecified", "");

        companion object {
            fun getEnum(value: String?): Event {
                for (v in values()) if (v.intent == value) return v
                return Unspecified
            }
        }
    }
}