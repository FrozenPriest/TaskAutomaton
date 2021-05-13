package ru.frozenpriest.taskautomaton.presentation.ui.trigger_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TriggerListFragment : Fragment() {
    private val viewModel: TriggerListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val programs by viewModel.allTriggers.observeAsState()
                LazyColumn() {
                    items(items = programs ?: emptyList()) { item ->
                        Text(text = item.connectedProgramId.toString())
                    }
                }
//                ProgramList(
//                    programs = programs ?: emptyList(),
//                    onNavigateToDetailsScreen = {
//                        val bundle = Bundle().apply {
//                            putLong("programId", it)
//                        }
//                        findNavController().navigate(R.id.view_program_details, bundle)
//                    },
//                    onAddNewProgram = { name -> viewModel.insertProgram(name) },
//                    onRenameProgram = { program -> viewModel.updateProgram(program) },
//                    onDeleteProgram = { program -> viewModel.deleteProgram(program) }
//                )
            }
        }
    }
}