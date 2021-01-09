package ru.frozenpriest.taskautomaton.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.base.entity.Task

class TaskViewModel(private val repository: TaskRepository): ViewModel() {
    val tasks: LiveData<List<Task>> = repository.readAllTasks

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}