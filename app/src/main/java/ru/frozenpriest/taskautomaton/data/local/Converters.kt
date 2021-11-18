package ru.frozenpriest.taskautomaton.data.local

import androidx.room.TypeConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.triggers.Trigger


object Converters {

    @TypeConverter
    fun fromCommandList(commands: List<Command>): String {
        val mapper = ObjectMapper()
        return mapper.writerFor(object : TypeReference<List<Command>>() {}).writeValueAsString(commands)
    }

    @TypeConverter
    fun toCommandList(commands: String): List<Command> {
        val mapper = ObjectMapper()
        return mapper.readValue(commands, object : TypeReference<List<Command>>() {})
    }

    @TypeConverter
    fun fromTrigger(trigger: Trigger): String {
        val mapper = ObjectMapper().apply {
//            addMixIn(Uri::class.java, UriMixIn::class.java)
        }
        return mapper.writerFor(object : TypeReference<Trigger>() {}).writeValueAsString(trigger)
    }

    @TypeConverter
    fun toTrigger(triggerString: String): Trigger {
        val mapper = ObjectMapper().apply {
//            addMixIn(Uri::class.java, UriMixIn::class.java)
        }
        return mapper.readValue(triggerString, object : TypeReference<Trigger>() {})
    }
}