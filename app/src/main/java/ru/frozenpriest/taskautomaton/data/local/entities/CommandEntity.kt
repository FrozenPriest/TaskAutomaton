package ru.frozenpriest.taskautomaton.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import ru.frozenpriest.taskautomaton.program.triggers.Trigger

@Entity(
    tableName = "table_triggers",
    foreignKeys = [ForeignKey(
        entity = ProgramEntity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("connectedProgramName"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Long,
    @SerialName("programName")
    val connectedProgramName: String,
    @SerialName("trigger")
    val trigger: Trigger
)