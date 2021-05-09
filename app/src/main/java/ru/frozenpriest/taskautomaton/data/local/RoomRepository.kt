package ru.frozenpriest.taskautomaton.data.local

import androidx.lifecycle.map
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.triggers.Trigger
import ru.frozenpriest.taskautomaton.utils.toEntity
import ru.frozenpriest.taskautomaton.utils.toProgram

class RoomRepository(private val programDao: ProgramDao, private val triggerDao: TriggerDao) {

    val allPrograms = programDao.getAllPrograms().map { list ->
        println("Gotcha!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        list.map {
            it.toProgram()
        }
    }


    val allTriggers = triggerDao.getAllTriggers()//todo map

    suspend fun insertProgram(program: Program) {
        programDao.insert(program.toEntity())
    }

    suspend fun insertTrigger(trigger: Trigger) {
        //TODO
    }
}


