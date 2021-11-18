package ru.frozenpriest.taskautomaton.presentation.ui.trigger_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.data.local.entities.TriggerEntity
import ru.frozenpriest.taskautomaton.presentation.components.TriggerEditor
import ru.frozenpriest.taskautomaton.presentation.components.TriggerList
import ru.frozenpriest.taskautomaton.presentation.ui.ActivityViewModel

@AndroidEntryPoint
class TriggerListFragment : Fragment() {
    private val viewModel: ActivityViewModel by activityViewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val triggers by viewModel.allTriggers.observeAsState()
                val programs by viewModel.allPrograms.observeAsState()
                var showEditDialog by remember { mutableStateOf(false) }
                var selectedTrigger by remember { mutableStateOf<TriggerEntity?>(null) }

                if (triggers == null || programs == null) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    TriggerList(
                        triggers = triggers!!,
                        programs = programs!!,
                        onNavigateToDetailsScreen = { trigger ->
                            selectedTrigger = trigger
                            showEditDialog = true
                        },
                        onSetTriggersProgram = { trigger, program ->
                            println("Setting program ${program.id} to trigger ${trigger.name}")
                            trigger.connectedProgramId = program.id
                            viewModel.updateTrigger(trigger)
                        },
                        onAddNewTrigger = { trigger ->
                            viewModel.insertTrigger(trigger)
                        },
                        onRenameTrigger = { trigger ->
                            viewModel.updateTrigger(trigger)
                        },
                        onDeleteTrigger = { trigger ->
                            viewModel.deleteTrigger(trigger)
                        }
                    )

                }
                if (showEditDialog) {
                    selectedTrigger?.let {
                        Dialog(
                            onDismissRequest = { showEditDialog = false }
                        ) {
                            TriggerEditor(
                                trigger = it.trigger,
                                submit = {
                                    viewModel.updateTrigger(it)
                                    selectedTrigger = null
                                    showEditDialog = false
                                }
                            )
                        }
                    }
                    //todo show composable edit trigger

                }
            }
        }
    }
}