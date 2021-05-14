package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.timepicker.timepicker
import ru.frozenpriest.taskautomaton.program.service.LocationState
import ru.frozenpriest.taskautomaton.program.triggers.LocationTrigger
import ru.frozenpriest.taskautomaton.program.triggers.TimeTrigger
import ru.frozenpriest.taskautomaton.program.triggers.Trigger
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@Composable
@Preview(showBackground = true)
fun TriggerBuilderPreview() {
    TriggerBuilder(trigger = TimeTrigger(0, 0, mutableSetOf()), build = { /*TODO*/ }) {

    }
}

@Composable
@Preview(showBackground = true)
fun TriggerBuilderLocationPreview() {
    TriggerBuilder(
        trigger = LocationTrigger(
            59.972,
            30.3237,
            10.0,
            LocationState.Undefined,
            LocationTrigger.Type.Enter
        ), build = { /*TODO*/ }) {

    }
}

@Composable
fun TriggerEditor(
    trigger: Trigger,
    submit: () -> Unit
) {
    Card() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TriggerBuilderCore(trigger = trigger)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { submit() }) {
                    Text("Submit")
                }
            }
        }
    }
}

@Composable
fun TriggerBuilder(
    trigger: Trigger,
    build: () -> Unit,
    cancel: () -> Unit
) {
    Card() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TriggerBuilderCore(trigger = trigger)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {
                        build()
                    }
                ) {
                    Text("Add")
                }
                Button(onClick = { cancel() }) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
private fun TriggerBuilderCore(
    trigger: Trigger
) {

    when (trigger) {
        is LocationTrigger -> {
            LocationTriggerBuilder(trigger)
        }
        is TimeTrigger -> {
            TimeTriggerBuilder(trigger)
        }
    }

}

@Composable
fun TimeTriggerBuilder(
    trigger: TimeTrigger
) {
    val hour = remember { mutableStateOf(trigger.hour) }
    val minute = remember { mutableStateOf(trigger.minute) }

    val dialog = remember { MaterialDialog() }
    dialog.build {
        timepicker(
            is24HourClock = true
        ) { time ->
            trigger.hour = time.hour
            trigger.minute = time.minute

            hour.value = time.hour
            minute.value = time.minute
        }
        buttons {
            positiveButton("Ok") { }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { dialog.show() }) {
            Row() {
                Text(
                    text = "%02d:".format(hour.value),
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = "%02d".format(minute.value),
                    style = MaterialTheme.typography.h2
                )

            }
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DayOfWeek.values().forEach { day ->
                val checked = remember {
                    mutableStateOf(trigger.activeDays.contains(day))
                }
                IconToggleButton(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = !checked.value
                        if (checked.value) trigger.activeDays.add(day)
                        else trigger.activeDays.remove(day)
                    }
                ) {
                    val colorState = animateColorAsState(
                        targetValue = if (checked.value) MaterialTheme.colors.primary else Color.White
                    )
                    val textColorState = animateColorAsState(
                        targetValue = if (checked.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier
                            .background(
                                colorState.value,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(all = 4.dp),
                        text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = textColorState.value
                    )
                }
            }
        }
    }
}

@Composable
fun LocationTriggerBuilder(
    trigger: LocationTrigger
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Latitude",
                modifier = Modifier.weight(0.4f, false)
            )

            val (latitudeText, setLatitudeText) = remember {
                mutableStateOf(
                    TextFieldValue(
                        trigger.latitude.toString()
                    )
                )
            }

            TextField(
                value = latitudeText,
                onValueChange = {
                    setLatitudeText(it)
                    trigger.latitude = it.text.toDouble()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    .weight(0.6f, true)
                    .padding(all = 8.dp),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Longitude",
                modifier = Modifier.weight(0.4f, false)
            )
            val (longitudeText, setLongitudeText) = remember {
                mutableStateOf(
                    TextFieldValue(
                        trigger.longitude.toString()
                    )
                )
            }

            TextField(
                value = longitudeText,
                onValueChange = {
                    setLongitudeText(it)
                    trigger.longitude = it.text.toDouble()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    .weight(0.6f, true)
                    .padding(all = 8.dp),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Radius",
                modifier = Modifier.weight(0.4f, false)
            )
            val (radiusText, setRadiusText) = remember {
                mutableStateOf(
                    TextFieldValue(
                        trigger.radius.toString()
                    )
                )
            }

            TextField(
                value = radiusText,
                onValueChange = {
                    setRadiusText(it)
                    trigger.radius = it.text.toDouble()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    .weight(0.6f, true)
                    .padding(all = 8.dp),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Mode",
                modifier = Modifier.weight(0.4f, false)
            )

            Selector(
                currentValue = trigger.type.toString(),
                possibleValues = LocationTrigger.Type.values().toList(),
                showAsString = { it.name },
                onItemSelected = { trigger.type = it })
        }
    }
}
