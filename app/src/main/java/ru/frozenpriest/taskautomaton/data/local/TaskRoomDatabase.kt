package ru.frozenpriest.taskautomaton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity

@Database(
    entities = [ProgramEntity::class, TriggerEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskRoomDatabase: RoomDatabase() {
    abstract fun triggerDao(): TriggerDao
    abstract fun programDao(): ProgramDao

}