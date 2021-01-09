package ru.frozenpriest.taskautomaton.data.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.frozenpriest.taskautomaton.Action
import ru.frozenpriest.taskautomaton.Condition

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    val name:String,
    val enabled: Boolean,
    val condition: Condition,
    val actions: List<Action>
)
