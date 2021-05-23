package ru.frozenpriest.taskautomaton.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import ru.frozenpriest.taskautomaton.data.local.dao.ProgramDao
import ru.frozenpriest.taskautomaton.data.local.dao.TriggerDao
import ru.frozenpriest.taskautomaton.data.local.entities.ProgramEntity
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.commands.variables.SumVar
import ru.frozenpriest.taskautomaton.program.triggers.SimpleEventTrigger
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskRoomDatabaseTest {

    private lateinit var programDao: ProgramDao
    private lateinit var triggerDao: TriggerDao

    private lateinit var db: TaskRoomDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TaskRoomDatabase::class.java
        ).build()
        programDao = db.programDao()
        triggerDao = db.triggerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReadTriggerTest() = runBlocking {
        val trigger = TriggerEntity(
            name = "Test trigger",
            enabled = true,
            trigger = SimpleEventTrigger(SimpleEventTrigger.Event.AirplaneSwitched)
        )
        val id = triggerDao.insert(trigger)
        delay(2000)
        triggerDao.getTriggerById(id).observeForever {
            Assert.assertEquals(trigger.name, it.name)
            Assert.assertEquals(trigger.connectedProgramId, it.connectedProgramId)
            Assert.assertEquals(trigger.enabled, it.enabled)
            Assert.assertEquals(
                (trigger.trigger as SimpleEventTrigger).eventAction,
                (it.trigger as SimpleEventTrigger).eventAction
            )
            Assert.assertEquals(trigger.name, it.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeReadProgramTest() = runBlocking {
        val program = ProgramEntity(
            name = "ProgramName",
            commands = listOf(
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
            )
        )
        val id = programDao.insert(program)
        delay(2000)
        val it = programDao.getProgramById(id)

        Assert.assertEquals(program.name, it.name)
        Assert.assertEquals(program.commands.size, it.commands.size)
    }

    @Test
    @Throws(Exception::class)
    fun readAllProgramTest() = runBlocking {
        val program = ProgramEntity(
            name = "ProgramName",
            commands = listOf(
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
            )
        )
        val programsList = listOf(
            program,
            program.copy(name = "Test 2"),
            program.copy(name = "Test 3", commands = emptyList())
        )
        programDao.insertAll(programsList)
        delay(2000)
        val result = programDao.getAllPrograms()
        result.observeForever {
            Assert.assertEquals(programsList[2].name, it[0].name)
            Assert.assertEquals(programsList[2].commands.size, it[0].commands.size)

            Assert.assertEquals(programsList[1].name, it[1].name)
            Assert.assertEquals(programsList[1].commands.size, it[1].commands.size)

            Assert.assertEquals(programsList[0].name, it[2].name)
            Assert.assertEquals(programsList[0].commands.size, it[2].commands.size)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteProgramTest() = runBlocking {
        val program = ProgramEntity(
            name = "ProgramName",
            commands = listOf(
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
                SumVar("v1", "v2", "v3"),
            )
        )
        val programsList = listOf(
            program,
            program.copy(name = "Test 2"),
            program.copy(name = "Test 3", commands = emptyList())
        )
        programDao.insertAll(programsList)
        delay(2000)
        programDao.delete(programDao.getProgramById(2))
        delay(2000)
        val result = programDao.getAllPrograms()
        result.observeForever {
            Assert.assertEquals(2, it.size)
            Assert.assertEquals(programsList[2].name, it[0].name)
            Assert.assertEquals(programsList[2].commands.size, it[0].commands.size)

            Assert.assertEquals(programsList[0].name, it[1].name)
            Assert.assertEquals(programsList[0].commands.size, it[1].commands.size)
        }
    }
}