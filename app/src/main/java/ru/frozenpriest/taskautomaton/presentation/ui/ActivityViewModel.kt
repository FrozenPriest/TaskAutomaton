package ru.frozenpriest.taskautomaton.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.program.Program
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    val repository: RoomRepository,
) : ViewModel() {
    val allPrograms: LiveData<List<Program>> = repository.allPrograms
    val allTriggers: LiveData<List<TriggerEntity>> = repository.allTriggers

    fun insertProgram(program: Program) = viewModelScope.launch {
        repository.insertProgram(program)
    }

    fun insertProgram(name: String) = viewModelScope.launch {
        repository.insertProgram(
            Program(
                name = name,
                commands = emptyList()
            )
        )
    }

    fun updateProgram(program: Program) = viewModelScope.launch {
        repository.updateProgram(program)
    }

    fun deleteProgram(program: Program) = viewModelScope.launch {
        repository.deleteProgram(program)
    }
}