package ru.frozenpriest.taskautomaton.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.frozenpriest.taskautomaton.data.base.dao.TaskDao
import ru.frozenpriest.taskautomaton.data.base.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            val temp = instance
            if(temp != null){
                return temp
            }
            synchronized(this){
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "user_database"
                ).build()
                instance = newInstance
                return newInstance
            }
        }
    }
}