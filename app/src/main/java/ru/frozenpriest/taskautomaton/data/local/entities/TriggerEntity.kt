package ru.frozenpriest.taskautomaton.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
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
    val id: Long = 0,
    @SerialName("name")
    var name: String,
    @SerialName("programId")
    val connectedProgramId: Long,
    @JsonProperty("enabled")
    var enabled: Boolean,
    @SerialName("trigger")
    val trigger: Trigger
)