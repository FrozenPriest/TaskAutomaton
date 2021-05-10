package ru.frozenpriest.taskautomaton.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity

@Dao
interface ProgramDao {
    @Query("select * from table_programs where name like :name limit 1")
    fun getProgramByName(name: String): LiveData<ProgramEntity>

    @Query("select * from table_programs where id=:id")
    fun getProgramById(id: Long): LiveData<ProgramEntity>

    @Query("select * from table_programs order by id Desc")
    fun getAllPrograms(): LiveData<List<ProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProgramEntity)


    @Update
    suspend fun update(entity: ProgramEntity)

    @Update
    suspend fun updateAll(entities: List<ProgramEntity>)

    @Delete
    suspend fun delete(entity: ProgramEntity)

    @Delete
    suspend fun delete(entities: List<ProgramEntity>)

    @Query("delete from table_programs")
    suspend fun deleteAll()
}