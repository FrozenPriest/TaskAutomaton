package ru.frozenpriest.taskautomaton.data.base

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.frozenpriest.taskautomaton.data.TaskRepository


class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    private val dataBase by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(dataBase.taskDao()) }
}