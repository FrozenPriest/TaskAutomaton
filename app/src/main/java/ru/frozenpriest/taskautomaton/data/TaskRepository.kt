package ru.frozenpriest.taskautomaton.data

import androidx.lifecycle.LiveData
import ru.frozenpriest.taskautomaton.data.base.dao.TaskDao
import ru.frozenpriest.taskautomaton.data.base.entity.Task

class TaskRepository(private val  taskDao: TaskDao) {

    val readAllTasks: LiveData<List<Task>> = taskDao.readAllTasks()

    suspend fun insert(task: Task) = taskDao.insert(task)

}