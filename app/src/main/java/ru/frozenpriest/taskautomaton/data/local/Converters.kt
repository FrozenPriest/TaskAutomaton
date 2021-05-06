package ru.frozenpriest.taskautomaton.data.local

import androidx.room.TypeConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ru.frozenpriest.taskautomaton.program.commands.Command


object Converters {

    @TypeConverter
    fun fromCommandList(commands: List<Command>): String {
        val mapper = ObjectMapper()
        return mapper.writerFor(object : TypeReference<List<Command>>() {}).withDefaultPrettyPrinter().writeValueAsString(commands)
    }

    @TypeConverter
    fun toCommandList(commands: String): List<Command> {
        val mapper = ObjectMapper()
        return mapper.readValue(commands, object : TypeReference<List<Command>>() {})
    }


}