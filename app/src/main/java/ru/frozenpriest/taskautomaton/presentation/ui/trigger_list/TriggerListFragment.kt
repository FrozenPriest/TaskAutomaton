package ru.frozenpriest.taskautomaton.presentation.ui.trigger_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.components.TriggerList
import ru.frozenpriest.taskautomaton.presentation.ui.ActivityViewModel

@AndroidEntryPoint
class TriggerListFragment : Fragment() {
    private val viewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val triggers by viewModel.allTriggers.observeAsState()
                val programs by viewModel.allPrograms.observeAsState()

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
                        onNavigateToDetailsScreen = {
                            val bundle = Bundle().apply {
                                putLong("programId", it)
                            }
                            findNavController().navigate(R.id.view_program_details, bundle)
                        },
                        onSetTriggersProgram = {},
                        onAddNewTrigger = { },
                        onRenameTrigger = { },
                        onDeleteTrigger = { }
                    )
                }
            }
        }
    }
}