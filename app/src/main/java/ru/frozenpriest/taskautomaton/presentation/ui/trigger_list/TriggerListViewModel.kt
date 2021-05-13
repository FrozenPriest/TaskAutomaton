package ru.frozenpriest.taskautomaton.presentation.ui.trigger_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.frozenpriest.taskautomaton.data.local.RoomRepository
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import javax.inject.Inject

@HiltViewModel
class TriggerListViewModel @Inject constructor(
    val repository: RoomRepository,
) : ViewModel() {
    val allTriggers: LiveData<List<TriggerEntity>> = repository.allTriggers


}