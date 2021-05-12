package ru.frozenpriest.taskautomaton.presentation.ui.program

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val repository: RoomRepository,
) : ViewModel() {

    lateinit var program: LiveData<Program>
    val loading = mutableStateOf(false)

    fun isSyntaxValid() = program.value?.isSyntaxValid ?: false
    fun loadProgram(id: Long) = viewModelScope.launch {
        loading.value = true

        program = repository.getProgram(id)

        loading.value = false
    }

    fun stop() {
        program.value?.stop()
    }

    fun nextCommand(context: Context) {
        program.value?.nextCommand(context)
    }

    fun executeCommands(context: Context) {
        program.value?.executeCommands(context)
    }

    fun updateProgram() = viewModelScope.launch {
        program.value?.let {
            repository.updateProgram(it)
        }
    }

    fun addCommands(commands: List<Command>) = viewModelScope.launch {
        program.value?.let {
            (it.commands as MutableList).addAll(commands)
            repository.updateProgram(it)
        }
    }
}