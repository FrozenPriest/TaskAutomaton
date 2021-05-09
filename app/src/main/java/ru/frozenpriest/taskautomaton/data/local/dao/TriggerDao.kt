package ru.frozenpriest.taskautomaton.data.local.dao

import androidx.room.*
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity

@Dao
interface TriggerDao {
    @Query("select * from table_triggers where id=:id")
    fun getTriggerById(id: Int): TriggerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TriggerEntity)

    @Update
    fun update(entity: TriggerEntity)

    @Update
    fun updateAll(entities: List<TriggerEntity>)

    @Delete
    fun delete(entity: TriggerEntity)

    @Delete
    fun delete(entities: List<TriggerEntity>)

    @Query("delete from table_triggers")
    fun deleteAll()
}