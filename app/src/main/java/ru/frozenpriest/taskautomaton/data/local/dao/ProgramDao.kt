package ru.frozenpriest.taskautomaton.data.local.dao

import androidx.room.*
import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity

@Dao
interface ProgramDao {
    @Query("select * from table_programs where name like :name limit 1")
    fun getProgramByName(name: String): ProgramEntity

    @Query("select * from table_programs where id=:id")
    fun getProgramById(id: Int): ProgramEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ProgramEntity)


    @Update
    fun update(entity: ProgramEntity)

    @Update
    fun updateAll(entities: List<ProgramEntity>)

    @Delete
    fun delete(entity: ProgramEntity)

    @Delete
    fun delete(entities: List<ProgramEntity>)

    @Query("delete from table_programs")
    fun deleteAll()
}