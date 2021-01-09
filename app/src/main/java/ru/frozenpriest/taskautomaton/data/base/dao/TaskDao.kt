package ru.frozenpriest.taskautomaton.data.base.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.frozenpriest.taskautomaton.data.base.entity.Task


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks ORDER BY _id ASC")
    fun readAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE _id = :id")
    fun getById(id: Long): Task?

    @Insert
    fun insert(employee: Task?)

    @Update
    fun update(employee: Task?)

    @Delete
    fun delete(employee: Task?)
}