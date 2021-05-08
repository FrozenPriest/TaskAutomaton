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
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("connectedProgramId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Long,
    @SerialName("programId")
    val connectedProgramId: Long,
    @SerialName("trigger")
    val trigger: Trigger
)