package ru.frozenpriest.taskautomaton.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import ru.frozenpriest.taskautomaton.program.commands.Command

@Entity(tableName = "table_programs")
data class ProgramEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "commands")
    val commands: List<Command>
)
