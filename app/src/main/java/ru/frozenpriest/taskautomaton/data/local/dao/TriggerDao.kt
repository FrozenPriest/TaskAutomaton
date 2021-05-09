package ru.frozenpriest.taskautomaton.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity

@Dao
interface TriggerDao {
    @Query("select * from table_triggers where id=:id")
    fun getTriggerById(id: Int): LiveData<TriggerEntity>

    @Query("select * from table_triggers order by id ASC")
    fun getAllTriggers(): LiveData<List<TriggerEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TriggerEntity)

    @Update
    suspend fun update(entity: TriggerEntity)

    @Update
    suspend fun updateAll(entities: List<TriggerEntity>)

    @Delete
    suspend fun delete(entity: TriggerEntity)

    @Delete
    suspend fun delete(entities: List<TriggerEntity>)

    @Query("delete from table_triggers")
    suspend fun deleteAll()
}