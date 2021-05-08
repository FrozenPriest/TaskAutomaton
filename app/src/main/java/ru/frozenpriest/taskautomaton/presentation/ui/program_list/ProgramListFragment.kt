package ru.frozenpriest.taskautomaton.presentation.ui.program_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.components.ProgramList
import ru.frozenpriest.taskautomaton.program.Program

class ProgramListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val programs = listOf(Program(1, "1", emptyList()))
                ProgramList(
                    programs = programs,
                    onNavigateToDetailsScreen = {
                        val bundle = Bundle().apply {
                            putLong("programId", it)
                        }
                        findNavController().navigate(R.id.view_program_details, bundle)
                    }
                )
            }
        }
    }
}