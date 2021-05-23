package ru.frozenpriest.taskautomaton.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.utils.toEntity
import ru.frozenpriest.taskautomaton.utils.toProgram

class RoomRepository(private val programDao: ProgramDao, private val triggerDao: TriggerDao) {

    val allPrograms = programDao.getAllPrograms().map { list ->
        list.map {
            it.toProgram()
        }
    }


    val allTriggers = triggerDao.getAllTriggers()

    suspend fun insertProgram(program: Program) = withContext(Dispatchers.IO) {
        programDao.insert(program.toEntity())
    }

    suspend fun updateProgram(program: Program) = withContext(Dispatchers.IO) {
        programDao.update(program.toEntity())
    }

    suspend fun insertTrigger(trigger: TriggerEntity) = withContext(Dispatchers.IO) {
        triggerDao.insert(trigger)
    }

    suspend fun deleteProgram(program: Program) = withContext(Dispatchers.IO) {
        programDao.delete(program.toEntity())
    }

    suspend fun getProgram(id: Long): Program = withContext(Dispatchers.IO) {
        return@withContext programDao.getProgramById(id).toProgram()
    }

    suspend fun updateTrigger(triggerEntity: TriggerEntity) = withContext(Dispatchers.IO) {
        triggerDao.update(triggerEntity)
    }

    suspend fun deleteTrigger(triggerEntity: TriggerEntity) = withContext(Dispatchers.IO) {
        triggerDao.delete(triggerEntity)
    }

    suspend fun getTrigger(id: Long): LiveData<TriggerEntity> = withContext(Dispatchers.IO) {
        triggerDao.getTriggerById(id)
    }
}


