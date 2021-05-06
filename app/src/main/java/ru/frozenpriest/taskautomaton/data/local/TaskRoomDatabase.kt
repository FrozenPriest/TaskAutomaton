package ru.frozenpriest.taskautomaton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity

@Database(
    entities = [ProgramEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskRoomDatabase: RoomDatabase() {
}