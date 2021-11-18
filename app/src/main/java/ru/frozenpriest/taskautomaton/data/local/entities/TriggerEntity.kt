package ru.frozenpriest.taskautomaton.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.frozenpriest.taskautomaton.program.triggers.Trigger

@Entity(
    tableName = "table_triggers",
    foreignKeys = [ForeignKey(
        entity = ProgramEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("programId"),
        onDelete = ForeignKey.SET_DEFAULT
    )]
)
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "programId", index = true)
    var connectedProgramId: Long? = null,
    @ColumnInfo(name = "enabled")
    var enabled: Boolean,
    @ColumnInfo(name = "trigger")
    val trigger: Trigger
)