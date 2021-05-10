package ru.frozenpriest.taskautomaton.presentation.ui.program_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.components.ProgramList

@AndroidEntryPoint
class ProgramListFragment : Fragment() {
    private val viewModel: ProgramListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val programs by viewModel.allPrograms.observeAsState()
                ProgramList(
                    programs = programs ?: emptyList(),
                    onNavigateToDetailsScreen = {
                        val bundle = Bundle().apply {
                            putLong("programId", it)
                        }
                        findNavController().navigate(R.id.view_program_details, bundle)
                    },
                    onAddNewProgram = { name -> viewModel.insertProgram(name) },
                    onRenameProgram = { program -> viewModel.updateProgram(program) },
                    onDeleteProgram = { program -> viewModel.deleteProgram(program) }
                )
            }
        }
    }
}